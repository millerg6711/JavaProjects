package edu.ranken.gmiller.roomwordsamplelive.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ranken.gmiller.roomwordsamplelive.R;
import edu.ranken.gmiller.roomwordsamplelive.data.entity.Word;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mItems;
    private final LayoutInflater mInflater;

    public WordListAdapter(Context context, List<Word> items) {
        this.mItems = items;
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(List<Word> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mItems != null) {
            Word item = mItems.get(position);
            holder.bindTo(item);
        } else {
            holder.textView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
        }

        public void bindTo(Word item) {
            textView.setText(item.getWord());
        }
    }
}
