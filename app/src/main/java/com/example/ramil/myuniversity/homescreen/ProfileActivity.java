package com.example.ramil.myuniversity.homescreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.model.User;
import com.example.ramil.myuniversity.otherviews.EditProfileActivity;
import com.example.ramil.myuniversity.utils.SingleFragmentActivity;

public class ProfileActivity extends SingleFragmentActivity {

    private static final String EXTRA_USER = "ramil.myuniversity.homescreen.user";
    private static final int REQUEST_CODE_USER = 0;

    private User mUser;
    private ProfileFragment mFragment;

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        mUser = getIntent().getParcelableExtra(EXTRA_USER);
        mFragment = ProfileFragment.newInstance(mUser);
        return mFragment;
    }

    @Override
    protected void overrideContext() {
        mContext = ProfileActivity.this;
    }

    @Override
    protected void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTopToolbar.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mUser.getUsername());
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
                    case R.id.action_edit:
                        Intent intent = EditProfileActivity.newIntent(mContext, mUser);
                        startActivityForResult(intent, REQUEST_CODE_USER);
                        return true;
                    case R.id.action_sign_out:
                        signOut();
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String uid = mFirebaseUser.getUid();

        MenuInflater inflater = getMenuInflater();
        if (uid.equals(mUser.getUid())) {
            inflater.inflate(R.menu.profile_menu, menu);
        } else {
            inflater.inflate(R.menu.toolbar_menu, menu);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_USER) {
            if (data == null) {
                return;
            }

            mUser = EditProfileActivity.getChangedUser(data);
            mFragment.setUser(mUser);
        }
    }
}
