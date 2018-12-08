package com.example.ramil.myuniversity.auth;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivitySignupBinding;
import com.example.ramil.myuniversity.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private Context mContext;
    private ActivitySignupBinding mBinding;

    private String email, username, group, password;

    // Firebase vars
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUtil mFirebaseUtil;
    private DatabaseReference mReference;

    public static Intent newIntent(Context context) {
        return new Intent(context, SignupActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        mContext = SignupActivity.this;
        mFirebaseUtil = new FirebaseUtil(mContext);

        mBinding.signUpProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();
        setupToolbar();

        init();
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.layoutTop.toolbar;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        mBinding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mBinding.inputEmailEditText.getText().toString();
                username = mBinding.inputUsernameEditText.getText().toString();
                group = mBinding.inputGroupEditText.getText().toString();
                password = mBinding.inputPasswordEditText.getText().toString();

                if (isCorrectInput(email, username, group, password)) {
                    mBinding.signUpProgressBar.setVisibility(View.VISIBLE);

                    mFirebaseUtil.registerNewEmail(email, password, mBinding);
                }
            }
        });
    }

    private boolean isCorrectInput(String email, String name, String group, String password) {
        return (validateEmail(email) && validateUsername(name)
                && validateGroup(group) && validatePassword(password));
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        mReference = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) { // User is signed in
                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mFirebaseUtil.addNewUser(email, username, group);

                            Toast.makeText(mContext,
                                    "Signup successful. Sending verification email.",
                                    Toast.LENGTH_SHORT).show();

                            mAuth.signOut();

                            mBinding.signUpProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i(TAG, "onCancelled: writeNewUserData");
                        }
                    });

                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            mBinding.inputEmailEditText.setError(getString(R.string.empty_field));
            return false;
        } else {
            mBinding.inputEmailEditText.setError(null);
            return true;
        }
    }

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            mBinding.inputUsernameEditText.setError(getString(R.string.empty_field));
            return false;
        } else {
            mBinding.inputUsernameEditText.setError(null);
            return true;
        }
    }

    private boolean validateGroup(String group) {
        if (group.isEmpty()) {
            mBinding.inputGroupEditText.setError(getString(R.string.empty_field));
            return false;
        } else {
            mBinding.inputGroupEditText.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String password) {
        if (password.length() < 6) {
            mBinding.inputPasswordEditText.setError(getString(R.string.small_password));
            return false;
        } else {
            mBinding.inputPasswordEditText.setError(null);
            return true;
        }
    }
}
