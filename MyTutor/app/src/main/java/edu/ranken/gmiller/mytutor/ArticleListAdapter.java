package edu.ranken.gmiller.mytutor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {
    private static final String LOG_TAG = ArticleListAdapter.class.getSimpleName();

    private final ArrayList<Article> mArticles;
    private final LayoutInflater mInflater;
    private final Context mContext;

    public ArticleListAdapter(Context context, ArrayList<Article> articles) {
        mArticles = articles;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleView = mInflater.inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListAdapter.ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.mTitleText.setText(article.getTitle());
        holder.mAuthorText.setText(article.getAuthor());
        SimpleDateFormat fm = new SimpleDateFormat(mContext.getString(R.string.date_format), Locale.getDefault());
        holder.mDateText.setText(fm.format(article.getPublishedDate().getTime()));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleText;
        private TextView mAuthorText;
        private TextView mDateText;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.article_title);
            mAuthorText = itemView.findViewById(R.id.article_author);
            mDateText = itemView.findViewById(R.id.article_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Article article = mArticles.get(getAdapterPosition());
            Uri webPage = Uri.parse(article.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            } else {
                Log.d(LOG_TAG, mContext.getString(R.string.bad_url_msg));
                Toast.makeText(mContext, mContext.getString(R.string.bad_url_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
