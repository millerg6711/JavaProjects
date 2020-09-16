package edu.ranken.gmiller.mytutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TabFragment extends Fragment {
    private static final String ARTICLES_EXTRA =
        "edu.ranken.gmiller.mytutor.extra.ARTICLES";

    private ArticleTopic mArticleTopic;
    private RecyclerView mRecyclerView;
    private ArticleListAdapter mAdapter;
    private ArrayList<Article> mArticles;

    public TabFragment() {
    }

    public TabFragment(ArticleTopic articleTopic) {
        mArticleTopic = articleTopic;
        Bundle bundle = new Bundle();
        bundle.putSerializable(HomeActivity.TOPIC, articleTopic);
        this.setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
//            if (savedInstanceState == null) {
                switch (mArticleTopic) {
                    case ANDROID:
                        mArticles = activity.getAndroidArticles();
                        break;
                    case JAVA:
                        mArticles = activity.getJavaArticles();
                        break;
                    case WEBDEV:
                        mArticles = activity.getWebDevArticles();
                        break;
                    default:
                        mArticles = new ArrayList<>();
                        break;
                }
//            } else {
//                mArticles = savedInstanceState.getParcelableArrayList(ARTICLES_EXTRA);
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ArticleListAdapter(getActivity(), mArticles);
        mRecyclerView.setAdapter(mAdapter);
        // Notify the adapter of the change.
        //mAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        HomeActivity activity = (HomeActivity) getActivity();
//
//        if (activity != null) {
//            outState.putParcelableArrayList(ARTICLES_EXTRA, mArticles);
//        }
    }

    public void updateData() {
        int count = mArticles.size();
        mAdapter.notifyItemChanged(count);
        mRecyclerView.smoothScrollToPosition(count);
    }
}
