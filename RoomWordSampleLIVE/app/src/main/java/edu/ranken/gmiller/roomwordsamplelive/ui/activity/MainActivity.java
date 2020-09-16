package edu.ranken.gmiller.roomwordsamplelive.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.ranken.gmiller.roomwordsamplelive.R;
import edu.ranken.gmiller.roomwordsamplelive.ui.adapter.WordListAdapter;
import edu.ranken.gmiller.roomwordsamplelive.data.WordRepository;
import edu.ranken.gmiller.roomwordsamplelive.ui.viewmodel.WordViewModel;
import edu.ranken.gmiller.roomwordsamplelive.data.entity.Word;

public class MainActivity extends AppCompatActivity {

     /********************************************************************************
     *
     *
     *
     *
     *
     * SEE RoomWordsSamplePartB Project for 10.1B Tasks and Coding Challenge
     * I did 10.1B before the live stream happened to have more time on my final.
     *
     *
     *
     *
     * ******************************************************************************/

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_NEW_WORD = 1;

    /*private WordApp mApp;*/
    /*private WordRepository mRepo;*/
    private WordViewModel mWordViewModel;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    private LiveData<List<Word>> mWords;
    private LocalBroadcastManager mBroadcastManager;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WordRepository.ACTION_ERROR.equals(action)) {
                String message = intent.getStringExtra(WordRepository.EXTRA_MESSAGE);
                displayToast(message);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        /*mApp = (WordApp) getApplication();
        mRepo = mApp.getRepo();*/
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWords = mWordViewModel.getAllWords();
        //List<Word> words = mDatabase.getWordDao().getAllWords();
        mBroadcastManager = LocalBroadcastManager.getInstance(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new WordListAdapter(this, mWords.getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        //new GetAllWordsTask().execute();
        mWords.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                mAdapter.setItems(words);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WordRepository.ACTION_ERROR);
        mBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        mBroadcastManager.unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertWord(View view) {
        startActivityForResult(new Intent(this, NewWordActivity.class), REQUEST_NEW_WORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEW_WORD && resultCode == RESULT_OK) {
            String word = data.getStringExtra(NewWordActivity.EXTRA_WORD);
            displayToast(word);
            //new InsertWordTask().execute(new Word(word));
            mWordViewModel.insertWord(new Word(word));
        }
    }

    private void displayToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    /*private class GetAllWordsTask extends AsyncTask<Void, Void, AsyncTaskResult<List<Word>>> {

        @Override
        protected AsyncTaskResult<List<Word>> doInBackground(Void... params) {
            try {
                List<Word> words =  mDatabase.getWordDao().getAllWords();
                return new AsyncTaskResult<>(words);
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground: Failed to get all words", ex);
                return new AsyncTaskResult<>(ex);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<List<Word>> result) {
            if (result.hasError()) {
                displayToast(getString(R.string.failed_retrieving_words));
            } else {
                mWords = result.getResult();
                mAdapter.setItems(mWords);
            }
        }
    }*/

    /*private class InsertWordTask extends AsyncTask<Word, Void, AsyncTaskResult<Word>> {

        @Override
        protected AsyncTaskResult<Word> doInBackground(Word... params) {
            Word word = params[0];
            try {
                mDatabase.getWordDao().insert(word);
                return new AsyncTaskResult<>(word);
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground: Failed to Insert Word", ex);
                return new AsyncTaskResult<>(ex);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Word>  result) {
            if (result.hasError()) {
                if (result.getError() instanceof SQLiteConstraintException) {
                    displayToast(getString(R.string.word_in_database_error));
                } else {
                    displayToast(getString(R.string.failed_insert_word_error));
                }
            } *//*else {
                Word word = result.getResult();
                mWords.add(word);
                mAdapter.notifyDataSetChanged();
            }*//*
        }
    }*/
}
