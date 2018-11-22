package com.example.ramil.myuniversity;

import android.support.v4.app.Fragment;

public class NewsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NewsFragment.newInstance();
    }

}
