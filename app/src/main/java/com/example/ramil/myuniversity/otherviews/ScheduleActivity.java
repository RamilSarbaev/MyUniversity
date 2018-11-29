package com.example.ramil.myuniversity.otherviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.utils.BaseActivity;

public class ScheduleActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ScheduleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mContext = ScheduleActivity.this;

        setupFirebaseAuth();
    }
}
