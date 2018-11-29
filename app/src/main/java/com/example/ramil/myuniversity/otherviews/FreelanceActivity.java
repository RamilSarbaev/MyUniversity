package com.example.ramil.myuniversity.otherviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FreelanceActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, FreelanceActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
