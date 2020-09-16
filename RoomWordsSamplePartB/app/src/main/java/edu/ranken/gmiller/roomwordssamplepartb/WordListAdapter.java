package edu.ranken.gmiller.roomwordssamplepartb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Word> mWords;
    private Context mContext;

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.mWordItem.setText(current.getWord());
        } else {
            holder.mWordItem.setText(R.string.no_word);
        }
    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null) { return mWords.size(); }
        return 0;
    }

    public Word getWordAtPosition(int position) {
        return mWords.get(position);
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mWordItem;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            mWordItem = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Word word = mWords.get(getAdapterPosition());
            Intent intent = new Intent(mContext, NewWordActivity.class);
            intent.putExtra("word", word);
            mContext.startActivity(intent);
        }
    }
}
