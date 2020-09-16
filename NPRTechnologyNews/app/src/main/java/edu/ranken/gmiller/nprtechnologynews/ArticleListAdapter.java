package edu.ranken.gmiller.nprtechnologynews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private static final String TAG = ArticleListAdapter.class.getSimpleName();

    private final List<Article> mArticles;
    private final LayoutInflater mInflater;
    private final Context mContext;

    public ArticleListAdapter(Context context, List<Article> articles) {
        this.mArticles = articles;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);

        if (article.getImageUrl() != null) {
            Picasso.get()
                    .load(article.getImageUrl())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .resize(256, 256)
                    .into(holder.mArticleImage);
        }

        holder.mArticleTitle.setText(article.getTitle());
        holder.mArticleDesc.setText(mContext
                .getString(R.string.summary_format,
                        article.getSummary().length() >= 40 ? article.getSummary().substring(0, 40) : article.getSummary()));
        holder.mArticleAuthor.setText(article.getAuthor().getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        holder.mArticleDate.setText(format.format(article.getDatePublished()));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mArticleImage;
        private final TextView mArticleDate;
        private final TextView mArticleAuthor;
        private final TextView mArticleTitle;
        private final TextView mArticleDesc;

        public ArticleViewHolder(@NonNull View view) {
            super(view);

            mArticleImage = view.findViewById(R.id.image_article);
            mArticleDate = view.findViewById(R.id.article_date);
            mArticleAuthor = view.findViewById(R.id.article_author);
            mArticleTitle = view.findViewById(R.id.article_title);
            mArticleDesc = view.findViewById(R.id.article_desc);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Article item = mArticles.get(position);
            try {
                Uri uri = Uri.parse(item.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            } catch (Exception ex) {
                Log.e(TAG, "onClick: Invalid Link", ex);
                Toast.makeText(mContext, R.string.invalid_link, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
