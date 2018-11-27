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

import com.example.ramil.myuniversity.BaseActivity;
import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding mBinding;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        LoginClickHandlers mHandlers = new LoginClickHandlers(this);
        mBinding.setHandlers(mHandlers);

        mBinding.loginProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    public class LoginClickHandlers {

        Context context;

        public LoginClickHandlers(Context context) {
            this.context = context;
        }

        public void onLoginButtonClicked(View view) {
            String email = mBinding.inputEmailEditText.getText().toString();
            String password = mBinding.inputPasswordEditText.getText().toString();

            if (!isCorrectInput(email, password)) {
                return;
            }

            mBinding.loginProgressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                                mBinding.loginProgressBar.setVisibility(View.GONE);
                            } else {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");

                                try {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        Intent intent = new Intent(context, BaseActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(context,
                                                "E-mail is not verified \n" +
                                                        "check your email inbox.",
                                                Toast.LENGTH_SHORT).show();
                                        mBinding.loginProgressBar.setVisibility(View.GONE);

                                        mAuth.signOut();
                                    }
                                } catch (NullPointerException e) {
                                    Log.e(TAG, "onComplete: " + e.getMessage());
                                }
                            }
                        }
                    });
        }

        public void onLinkSignupClicked(View view) {
            Intent intent = new Intent(context, SignupActivity.class);
            startActivity(intent);
        }
    }

    private boolean isCorrectInput(String email, String password) {
        if (email.isEmpty()) {
            mBinding.emailTextInputLayout.setError(getString(R.string.empty_field));
            return false;
        } else
            mBinding.emailTextInputLayout.setError(null);

        if (password.length() < 6) {
            mBinding.passwordTextInputLayout.setError(getString(R.string.small_password));
            return false;
        } else
            mBinding.passwordTextInputLayout.setError(null);

        return true;
    }
}
