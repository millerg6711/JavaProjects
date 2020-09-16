package edu.ranken.gmiller.recyclerview;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mWordList;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize world list
        mWordList = new ArrayList<>();

        // get widgets
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recyclerview);

        // configure toolbar
        setSupportActionBar(mToolbar);

        // populate list
        buildWordList();

        // setup layout manager
        int gridColumnCount =
            getResources().getInteger(R.integer.grid_column_count);
        mRecyclerView.setLayoutManager(
            new GridLayoutManager(this, gridColumnCount));

        //fab
        mFab.setOnClickListener(view -> {
            int count = mWordList.size();
            mWordList.add("+ Word " + count);
            mAdapter.notifyItemInserted(count);
            mRecyclerView.smoothScrollToPosition(count);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reset_option) {
            buildWordList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("words", mWordList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mWordList = savedInstanceState.getStringArrayList("words");
        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void buildWordList() {
        if (!mWordList.isEmpty()) {
            mWordList.clear();
        }

        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);

        for (int i = 0; i < 20; i++) {
            mWordList.add("Word " + (i + 1));
        }
    }
}
