package com.example.ramil.myuniversity.otherviews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.utils.SingleFragmentActivity;

public class ChatActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ChatActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return ChatFragment.newInstance();
    }

    @Override
    protected void overrideContext() {
        mContext = ChatActivity.this;
    }

    @Override
    protected void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTopToolbar.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.chat_item);
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
