package edu.ranken.gmiller.chatterbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder> {
    private final List<Message> mChatMessages;
    private final LayoutInflater mInflater;
    private final Context mContext;

    public ChatMessagesAdapter(Context context, List<Message> messages) {
        this.mChatMessages = messages;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View messageView = mInflater.inflate(R.layout.chat_message_item, parent, false);
        return new MessageViewHolder(messageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = mChatMessages.get(position);
        holder.mChatMessage.setText(
                mContext.getString(R.string.chat_message_format, message.getDateStr(), message.getUser(), message.getMsg()));
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView mChatMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mChatMessage = itemView.findViewById(R.id.chat_message);
        }
    }
}
