package com.example.ramil.myuniversity.auth;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ramil.myuniversity.NewsActivity;
import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.SingleFragmentActivity;
import com.example.ramil.myuniversity.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private Context mContext;
    private ActivityLoginBinding binding;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mContext = LoginActivity.this;

        // TODO DataBinding visibilities and clickListeners

        binding.loginProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();

        init();
    }

    private void init() {
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.inputEmailEditText.getText().toString();
                String password = binding.inputPasswordEditText.getText().toString();

                if (isIncorrectInput(email, password)) {
                    Toast.makeText(mContext, "You must fill out all the fields.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    binding.loginProgressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                // If sign in fails, display a message to the user.
                                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                                Toast.makeText(mContext, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();

                                                binding.loginProgressBar.setVisibility(View.GONE);
                                            } else {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d(TAG, "signInWithEmail:success");

                                                try {
                                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                                        Intent intent = new Intent(mContext, NewsActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(mContext,
                                                                "E-mail is not verified \n" +
                                                                        "check your email inbox.",
                                                                Toast.LENGTH_SHORT).show();
                                                        binding.loginProgressBar.setVisibility(View.GONE);

                                                        mAuth.signOut();
                                                    }
                                                } catch (NullPointerException e) {
                                                    Log.e(TAG, "onComplete: " + e.getMessage());
                                                }
                                            }
                                        }
                                    });
                }
            }
        });

        binding.linkSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isIncorrectInput(String email, String password) {
        return (email.equals("") || password.equals(""));
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }
}
