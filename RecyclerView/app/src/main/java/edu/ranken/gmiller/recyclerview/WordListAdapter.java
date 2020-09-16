package edu.ranken.gmiller.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private final List<String> mWordList;
    private final LayoutInflater mInflater;

    public WordListAdapter(Context context, List<String> mWorldList) {
        this.mWordList = mWorldList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String item = mWordList.get(position);
        holder.mWord.setText(item);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class WordViewHolder extends ViewHolder implements View.OnClickListener {
        public final TextView mWord;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            mWord = itemView.findViewById(R.id.word);
            mWord.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // get clicked item
            int index = getLayoutPosition();
            String item = mWordList.get(index);
            // modify clicked item
            mWordList.set(index, "Clicked! " + item);
            // notify adapter that list has changed
            WordListAdapter.this.notifyItemChanged(index);
        }
    }
}
