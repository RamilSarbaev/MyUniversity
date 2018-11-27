package com.example.ramil.myuniversity.auth;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    private ActivitySignupBinding binding;

    private String email, username, group, password;

    // Firebase vars
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUtil firebaseUtil;
    //private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        mContext = SignupActivity.this;
        firebaseUtil = new FirebaseUtil(mContext);

        binding.signUpProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();

        init();
    }

    private void init() {
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.inputEmailEditText.getText().toString();
                username = binding.inputUsernameEditText.getText().toString();
                group = binding.inputGroupEditText.getText().toString();
                password = binding.inputPasswordEditText.getText().toString();

                if (isCorrectInput(email, username, group, password)) {
                    binding.signUpProgressBar.setVisibility(View.VISIBLE);

                    firebaseUtil.registerNewEmail(email, password, binding);
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
                            firebaseUtil.addNewUser(email, username, group);

                            Toast.makeText(mContext,
                                    "Signup successful. Sending verification email.",
                                    Toast.LENGTH_SHORT).show();

                            mAuth.signOut();

                            binding.signUpProgressBar.setVisibility(View.GONE);
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
            binding.inputEmailEditText.setError(getString(R.string.empty_field));
            return false;
        } else {
            binding.inputEmailEditText.setError(null);
            return true;
        }
    }

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            binding.inputUsernameEditText.setError(getString(R.string.empty_field));
            return false;
        } else {
            binding.inputUsernameEditText.setError(null);
            return true;
        }
    }

    private boolean validateGroup(String group) {
        if (group.isEmpty()) {
            binding.inputGroupEditText.setError(getString(R.string.empty_field));
            return false;
        } else {
            binding.inputGroupEditText.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String password) {
        if (password.length() < 6) {
            binding.inputPasswordEditText.setError(getString(R.string.small_password));
            return false;
        } else {
            binding.inputPasswordEditText.setError(null);
            return true;
        }
    }
}
