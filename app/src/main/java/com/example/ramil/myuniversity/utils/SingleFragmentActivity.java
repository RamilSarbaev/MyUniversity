package com.example.ramil.myuniversity.utils;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivityFragmentBinding;

public abstract class SingleFragmentActivity extends BaseActivity {

    protected abstract Fragment createFragment();
    protected abstract void overrideContext();
    protected abstract void setupToolbar();

    protected ActivityFragmentBinding mBinding;

    //Сообщаем AS, что реализация метода должна возвращать действ.идентификатор ресурса макета
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil
                .setContentView(this, getLayoutResId());

        overrideContext();

        setupFirebaseAuth();

        attachFragment();

        setupToolbar();
    }

    private void attachFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.single_fragment_container, fragment)
                    .commit();
        }
    }
}
