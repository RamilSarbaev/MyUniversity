package com.example.ramil.myuniversity.homescreen;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivityHomeBinding;
import com.example.ramil.myuniversity.model.User;
import com.example.ramil.myuniversity.utils.BaseActivity;
import com.example.ramil.myuniversity.utils.BottomNavigationHelper;

public class HomeActivity extends BaseActivity implements MoreFragment.ProfileCallbacks,
        NewsFragment.NewsCallbacks {

    private ActivityHomeBinding mBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_home);

        mContext = HomeActivity.this;

        setupFirebaseAuth();
        setupToolbar();
        setupBottomNavigation();
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTopToolbar.toolbar;
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_sign_out:
                        signOut();
                        return true;
                }

                return false;
            }
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationHelper helper = new BottomNavigationHelper(HomeActivity.this);
        helper.enableNavigation(mBinding.bottomNavigationView);
        helper.initBaseActivity();
    }

    @Override
    public void onProfileSelected(User user) {
        /*ProfileFragment fragment = ProfileFragment.newInstance(user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container, fragment)
                .addToBackStack(null)
                .commit();*/

        startActivity(ProfileActivity.newIntent(mContext, user));
    }

    @Override
    public void onNewsClicked(String url) {
        /*NewsPageFragment fragment = NewsPageFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();*/

        startActivity(NewsPageActivity.newIntent(mContext, url));
    }
}
