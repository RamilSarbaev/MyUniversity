package com.example.ramil.myuniversity.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.homescreen.EventsFragment;
import com.example.ramil.myuniversity.homescreen.MoreFragment;
import com.example.ramil.myuniversity.homescreen.NewsFragment;
import com.example.ramil.myuniversity.homescreen.TasksFragment;

public class BottomNavigationHelper {

    // TODO improve Bottom Navigation (make visible all labels and change color of active item)

    // TODO fix reaction on changes in BackStack and Configuration(HeadFirst 457)

    private AppCompatActivity mActivity;
    private ActionBar mToolbar;
    private FragmentManager mFragmentManager;

    public BottomNavigationHelper(AppCompatActivity activity) {
        mActivity = activity;
        mToolbar = mActivity.getSupportActionBar();
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public void enableNavigation(BottomNavigationView view) {
        view.setItemHorizontalTranslationEnabled(false);
        //view.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_news:
                        mToolbar.setTitle(R.string.news_item);
                        Fragment newsFragment = NewsFragment.newInstance();
                        openFragment(newsFragment);
                        return true;
                    case R.id.navigation_events:
                        mToolbar.setTitle(R.string.events_item);
                        Fragment eventsFragment = EventsFragment.newInstance();
                        openFragment(eventsFragment);
                        return true;
                    case R.id.navigation_tasks:
                        mToolbar.setTitle(R.string.tasks_item);
                        Fragment tasksFragment = TasksFragment.newInstance();
                        openFragment(tasksFragment);
                        return true;
                    case R.id.navigation_more:
                        mToolbar.setTitle(R.string.more_item);
                        Fragment moreFragment = MoreFragment.newInstance();
                        openFragment(moreFragment);
                        return true;
                }

                return false;
            }
        });
    }

    public void initBaseActivity() {
        mToolbar.setTitle(R.string.news_item);
        openFragment(NewsFragment.newInstance());

    }

    private void openFragment(Fragment newFragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();
    }
}
