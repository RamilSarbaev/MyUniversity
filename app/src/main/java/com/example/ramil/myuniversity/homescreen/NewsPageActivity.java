package com.example.ramil.myuniversity.homescreen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.utils.SingleFragmentActivity;

public class NewsPageActivity extends SingleFragmentActivity {

    private static final String EXTRA_NEWS_URL = "com.example.ramil.myuniversity.news_url";

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, NewsPageActivity.class);
        intent.putExtra(EXTRA_NEWS_URL, url);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String url = getIntent().getStringExtra(EXTRA_NEWS_URL);
        return NewsPageFragment.newInstance(url);
    }

    @Override
    protected void overrideContext() {
        mContext = NewsPageActivity.this;
    }

    @Override
    protected void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTopToolbar.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("www.unn.ru");
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
}
