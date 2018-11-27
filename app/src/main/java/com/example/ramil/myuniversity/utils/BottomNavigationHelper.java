package com.example.ramil.myuniversity.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.ramil.myuniversity.EventsFragment;
import com.example.ramil.myuniversity.MoreFragment;
import com.example.ramil.myuniversity.NewsFragment;
import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.ScheduleFragment;
import com.example.ramil.myuniversity.TasksFragment;

public class BottomNavigationHelper {

    // TODO improve Bottom Navigation (make visible all labels and change color of active item)

    private static final String TAG = "BottomNavigationHelper";

    private AppCompatActivity mActivity;

    public BottomNavigationHelper(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void enableNavigation(BottomNavigationView view, final ActionBar toolbar) {
        toolbar.setTitle(R.string.news_item);
        openFragment(NewsFragment.newInstance());

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_news:
                        toolbar.setTitle(R.string.news_item);
                        Fragment newsFragment = NewsFragment.newInstance();
                        openFragment(newsFragment);
                        return true;
                    case R.id.navigation_events:
                        toolbar.setTitle(R.string.events_item);
                        Fragment eventsFragment = EventsFragment.newInstance();
                        openFragment(eventsFragment);
                        return true;
                    case R.id.navigation_tasks:
                        toolbar.setTitle(R.string.tasks_item);
                        Fragment tasksFragment = TasksFragment.newInstance();
                        openFragment(tasksFragment);
                        return true;
                    case R.id.navigation_schedule:
                        toolbar.setTitle(R.string.schedule_item);
                        Fragment scheduleFragment = ScheduleFragment.newInstance();
                        openFragment(scheduleFragment);
                        return true;
                    case R.id.navigation_more:
                        toolbar.setTitle(R.string.more_item);
                        Fragment moreFragment = MoreFragment.newInstance();
                        openFragment(moreFragment);
                        return true;
                }

                return false;
            }
        });
    }

    private void openFragment(Fragment newFragment) {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();
    }
}
