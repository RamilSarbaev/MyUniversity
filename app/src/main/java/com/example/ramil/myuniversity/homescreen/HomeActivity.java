package com.example.ramil.myuniversity.homescreen;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivityHomeBinding;
import com.example.ramil.myuniversity.model.UsersAccount;
import com.example.ramil.myuniversity.utils.BaseActivity;
import com.example.ramil.myuniversity.utils.BottomNavigationHelper;

public class HomeActivity extends BaseActivity implements MoreFragment.ProfileCallbacks,
        NewsFragment.NewsCallbacks {

    private ActivityHomeBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_home);

        mContext = HomeActivity.this;

        setupFirebaseAuth();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationHelper helper = new BottomNavigationHelper(HomeActivity.this);
        helper.enableNavigation(binding.bottomNavigationView);
        helper.initBaseActivity();
    }

    @Override
    public void onProfileSelected(UsersAccount account) {
        ProfileFragment fragment = ProfileFragment.newInstance(account);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onNewsClicked(String url) {
        NewsPageFragment fragment = NewsPageFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
