package edu.ranken.gmiller.nprtechnologynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mNprTechDesc;
    private RequestQueue mRequestQueue;
    private ArrayList<Article> mArticles;
    private RecyclerView mRecyclerView;
    private ArticleListAdapter mAdapter;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TechNewsApp.MESSAGES_ACTION.equals(action)) {
                try {
                    ArrayList<Article> articles = intent.getParcelableArrayListExtra(TechNewsApp.MESSAGES_EXTRA);
                    mArticles.clear();
                    mArticles.addAll(articles);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Toast.makeText(context, R.string.article_retrieval_error, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onReceive Broadcast: ", ex);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNprTechDesc = findViewById(R.id.npr_tech_desc);
        mRequestQueue = Volley.newRequestQueue(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mArticles = new ArrayList<>();
        mAdapter = new ArticleListAdapter(this, mArticles);
        mRecyclerView.setAdapter(mAdapter);

        mRequestQueue = Volley.newRequestQueue(this);

        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(mReceiver, new IntentFilter(TechNewsApp.MESSAGES_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();

        fetchArticles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                fetchArticles();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchArticles() {
        GsonRequest<Feed> request = new GsonRequest<>(TechNewsApp.API_URL, Feed.class,
            (Feed feed) -> {
                mArticles.clear();
                mNprTechDesc.setText(feed.getDescription());
                mArticles.addAll(feed.getArticles());
                mAdapter.notifyDataSetChanged();
            }, (VolleyError error) -> {
                Toast.makeText(MainActivity.this, R.string.article_retrieval_error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "fetchArticles: ", error);
            });
        mRequestQueue.add(request);
    }
}
