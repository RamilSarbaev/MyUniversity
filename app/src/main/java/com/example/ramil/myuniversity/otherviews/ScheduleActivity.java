package com.example.ramil.myuniversity.otherviews;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivityScheduleBinding;
import com.example.ramil.myuniversity.utils.BaseActivity;

public class ScheduleActivity extends BaseActivity {

    private ActivityScheduleBinding mBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ScheduleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_schedule);

        mContext = ScheduleActivity.this;

        setupFirebaseAuth();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTop.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.schedule_item);
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
