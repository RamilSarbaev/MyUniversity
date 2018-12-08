package com.example.ramil.myuniversity.otherviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivityEditProfileBinding;
import com.example.ramil.myuniversity.model.User;
import com.example.ramil.myuniversity.utils.BaseActivity;
import com.example.ramil.myuniversity.utils.FirebaseUtil;

public class EditProfileActivity extends BaseActivity {

    private static final String TAG = "EditProfileActivity";

    private static final String EXTRA_USER = "ramil.myuniversity.otherviews.user";
    private static final String EXTRA_CHANGED_USER = "ramil.myuniversity.otherviews.changed_user";
    private static final int REQUEST_IMAGE = 0;

    private ActivityEditProfileBinding mBinding;

    private User mUser;

    private FirebaseUtil mFirebaseUtil;

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

        mUser = getIntent().getParcelableExtra(EXTRA_USER);
        mBinding.setUser(mUser);
        mBinding.setHandlers(new EditProfileHandlers());

        mContext = EditProfileActivity.this;
        mFirebaseUtil = new FirebaseUtil(mContext);

        setupFirebaseAuth();
        setupToolbar();
        initSpinner();
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTop.toolbarEdit;
        setSupportActionBar(toolbar);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.genderSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public class EditProfileHandlers {

        public void onCancelClicked(View view) {
            finish();
        }

        public void onConfirmClicked(View view) {
            setProfileChanged();
            mFirebaseUtil.updateUsersData(mUser);

            finish();
        }

        public void onChangePhotoClicked(View view) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE);
        }
    }

    private void setProfileChanged() {
        Intent data = new Intent();
        data.putExtra(EXTRA_CHANGED_USER, mUser);
        setResult(RESULT_OK, data);
    }

    public static User getChangedUser(Intent result) {
        return result.getParcelableExtra(EXTRA_CHANGED_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i(TAG, "onActivityResult: requestCode = " + requestCode
                + ", resultCode = " + resultCode);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();

                Log.i(TAG, "onActivityResult: uri = " + uri.toString());

                mFirebaseUtil.putImageInStorage(uri, mUser);
            }
        }
    }
}
