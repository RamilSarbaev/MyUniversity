package com.example.ramil.myuniversity.utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.ramil.myuniversity.R;

public abstract class SingleFragmentActivity extends BaseActivity {

    protected abstract Fragment createFragment();

    protected abstract void overrideContext();

    //Сообщаем AS, что реализация метода должна возвращать действ.идентификатор ресурса макета
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        overrideContext();

        setupFirebaseAuth();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
