package com.example.ramil.myuniversity.otherviews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

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
}
