package com.example.ramil.myuniversity.otherviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.utils.BaseActivity;

public class MailActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, MailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        mContext = MailActivity.this;

        setupFirebaseAuth();
    }
}
