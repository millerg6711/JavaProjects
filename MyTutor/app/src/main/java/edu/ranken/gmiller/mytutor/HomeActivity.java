package edu.ranken.gmiller.mytutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private static final String ANDROID_ARTICLES_EXTRA =
        "edu.ranken.gmiller.mytutor.extra.ANDROID_ARTICLES";
    private static final String JAVA_ARTICLES_EXTRA =
        "edu.ranken.gmiller.mytutor.extra.JAVA_ARTICLES";
    private static final String WEBDEV_ARTICLES_EXTRA =
        "edu.ranken.gmiller.mytutor.extra.WEBDEV_ARTICLES";

    public static final String TOPIC =
        "edu.ranken.gmiller.mytutor.extra.TOPIC";
    public static final int ADD_ARTICLE_REQUEST = 1;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;

    private ArrayList<Article> mJavaArticles;
    private ArrayList<Article> mAndroidArticles;
    private ArrayList<Article> mWebDevArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get Widgets
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mFab = findViewById(R.id.fab);

        // Enable the action bar
        setSupportActionBar(mToolbar);

        //region Configure tab layout
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.topic_java));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.topic_android));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.topic_webdev));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Configure view pager
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        // Synchronize the TabLayout and ViewPager
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //endregion

        // Initialize the Article ArrayLists that will contain the data
        if (savedInstanceState != null) {
            mJavaArticles = savedInstanceState.getParcelableArrayList(JAVA_ARTICLES_EXTRA);
            mAndroidArticles = savedInstanceState.getParcelableArrayList(ANDROID_ARTICLES_EXTRA);
            mWebDevArticles = savedInstanceState.getParcelableArrayList(WEBDEV_ARTICLES_EXTRA);
        } else {

            mJavaArticles = new ArrayList<>();
            mAndroidArticles = new ArrayList<>();
            mWebDevArticles = new ArrayList<>();

            // Get data from the XML File
            String[] articleTopics =
                getResources().getStringArray(R.array.article_topics);
            String[] articleTitles =
                getResources().getStringArray(R.array.article_titles);
            String[] articleAuthors =
                getResources().getStringArray(R.array.article_authors);
            String[] articlePublishedDates =
                getResources().getStringArray(R.array.article_dates);
            String[] articleLinks =
                getResources().getStringArray(R.array.article_links);

            // Get Date Format
            SimpleDateFormat fm = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());

            // Loop through articles
            for (int i = 0; i < articleTitles.length; i++) {
                // Make new calendar instance
                Calendar calendar = Calendar.getInstance();
                try {
                    // Parse Date from XML File
                    Date date = fm.parse(articlePublishedDates[i]);
                    //if (date != null) {
                        // Set the date in calendar object
                        calendar.setTime(date);
                        //try {
                            // Create new Article
                            Article article = new Article(
                                articleTitles[i],
                                articleAuthors[i],
                                calendar,
                                articleTopics[i],
                                articleLinks[i]
                            );
                            // Populate Article ArrayLists
                            if (getString(R.string.topic_java).equals(articleTopics[i])) {
                                mJavaArticles.add(article);
                            } else if (getString(R.string.topic_android).equals(articleTopics[i])) {
                                mAndroidArticles.add(article);
                            } else if (getString(R.string.topic_webdev).equals(articleTopics[i])) {
                                mWebDevArticles.add(article);
                            }
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            Log.e(LOG_TAG, "ArrayIndexOutOfBoundsException", e);
//                        }
//                    }
                } catch (ParseException e) {
                    Log.e(LOG_TAG, "ParseException", e);
                }
            }
        }

        // Setup FAB Listener
        mFab.setAlpha(.6f);
        mFab.setOnClickListener(view -> {
            Intent newIntent = new Intent(HomeActivity.this, AddArticleActivity.class);
            startActivityForResult(newIntent, ADD_ARTICLE_REQUEST);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_ARTICLE_REQUEST) {
            Article article = data.getParcelableExtra(AddArticleActivity.ARTICLE_EXTRA);
            if (article != null) {
                for (Fragment f : getSupportFragmentManager().getFragments()) {
                    TabFragment fragment = (TabFragment) f;
                    if (fragment != null) {
                        Bundle bundle = fragment.getArguments();
                        if (bundle != null) {
                            ArticleTopic topic = (ArticleTopic) bundle.getSerializable(TOPIC);
                            if (topic != null) {
                                if (getString(topic.getResourceId()).equals(article.getTopic())) {
                                    switch (topic) {
                                        case JAVA:
                                            mJavaArticles.add(article);
                                            break;
                                        case ANDROID:
                                            mAndroidArticles.add(article);
                                            break;
                                        case WEBDEV:
                                            mWebDevArticles.add(article);
                                            break;
                                        default:
                                            break;
                                    }
                                    fragment.updateData();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(JAVA_ARTICLES_EXTRA, mJavaArticles);
        outState.putParcelableArrayList(ANDROID_ARTICLES_EXTRA, mAndroidArticles);
        outState.putParcelableArrayList(WEBDEV_ARTICLES_EXTRA, mWebDevArticles);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // THESE LISTS MUST BE RESTORED IN onCreate()
        //mJavaArticles = savedInstanceState.getParcelableArrayList(JAVA_ARTICLES_EXTRA);
        //mAndroidArticles = savedInstanceState.getParcelableArrayList(ANDROID_ARTICLES_EXTRA);
        //mWebDevArticles = savedInstanceState.getParcelableArrayList(WEBDEV_ARTICLES_EXTRA);
    }

    public ArrayList<Article> getJavaArticles() {
        return mJavaArticles;
    }

    public ArrayList<Article> getAndroidArticles() {
        return mAndroidArticles;
    }

    public ArrayList<Article> getWebDevArticles() {
        return mWebDevArticles;
    }
}
