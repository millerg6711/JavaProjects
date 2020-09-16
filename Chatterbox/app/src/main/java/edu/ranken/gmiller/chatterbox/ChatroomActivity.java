package edu.ranken.gmiller.chatterbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatroomActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private static final String TAG = ChatroomActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ChatMessagesAdapter mAdapter;
    private List<Message> mMessages;
    private RequestQueue mRequestQueue;
    private EditText mMessageInput;
    private Timer mTimer;

    private String mUsername;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ChatterApp.MESSAGES_ACTION.equals(action)) {
                try {
                    Message[] messages = (Message[]) intent.getParcelableArrayExtra(ChatterApp.MESSAGES_EXTRA);
                    if (messages != null) {
                        Message newMessage = messages[messages.length - 1];
                        if (!newMessage.getTime().equals(mMessages.get(mMessages.size() - 1).getTime())) {
                            mMessages.clear();
                            mMessages.addAll(Arrays.asList(messages));
                            mAdapter.notifyDataSetChanged();

                        }

                    }
                } catch (Exception ex) {
                    Toast.makeText(ChatroomActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Broadcast onReceive: Retrieved Messages", ex);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        mRecyclerView = findViewById(R.id.recycler_view);
        mMessageInput = findViewById(R.id.edit_message);

        Intent intent = getIntent();
        mUsername = intent.getStringExtra(ChatterApp.USERNAME_EXTRA);

        mMessageInput.setOnEditorActionListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        //manager.setReverseLayout(true); didn't need this
        mRecyclerView.setLayoutManager(manager);

        mMessages = new ArrayList<>();
        mAdapter = new ChatMessagesAdapter(this, mMessages);
        mRecyclerView.setAdapter(mAdapter);

        mRequestQueue = Volley.newRequestQueue(this);

        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(mReceiver, new IntentFilter(ChatterApp.MESSAGES_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getMessages();
            }
        };

        mTimer = new Timer(true);
        mTimer.schedule(task, 0, 30 * 1000);
    }

    private void getMessages() {
        String url = ChatterApp.API_BASE + ChatterApp.GET_MESSAGES_ENDPOINT;
        GsonRequest<Message[]> request = new GsonRequest<>(
                url, Message[].class,
                (Message[] messages) -> {
                    Message newestMessage = messages[messages.length - 1];
                    if (!mMessages.isEmpty()) {
                        Message lastUpdatedMessage = mMessages.get(mMessages.size() - 1);
                        if (!newestMessage.getTime().equals(lastUpdatedMessage.getTime()) &&
                            !newestMessage.getUser().equals(mUsername)) {
                            Log.d(TAG, "getMessages: SENT NOTIFICATION");
                            ChatterApp app = (ChatterApp) getApplication();
                            app.sendNotification(newestMessage);
                        }
                        mMessages.clear();
                    }
                    mMessages.addAll(Arrays.asList(messages));
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mMessages.size() - 1);
                }, (VolleyError error) -> {
                    Log.e(TAG, "onErrorResponse: ", error);
                });
        mRequestQueue.add(request);
    }

    private void sendMessage() {
        Message message = new Message(mUsername, mMessageInput.getText().toString());

        String url = ChatterApp.API_BASE + ChatterApp.POST_MESSAGE_ENDPOINT;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                (String response) -> {
                    getMessages();
                    if (!response.equals("POSTED")) {
                        Toast.makeText(ChatroomActivity.this, "Your message was blocked", Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "sendMessage: " + response);
                },
                (VolleyError error) -> {
                    Toast.makeText(ChatroomActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onErrorResponse: ", error);
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", message.getUser());
                params.put("message", message.getMsg());
                return params;
            }
        };
        mRequestQueue.add(request);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if (!TextUtils.isEmpty(mMessageInput.getText())) {
                sendMessage();
                mMessageInput.setText("");
            }
            return true;
        }
        return false;
    }
}
