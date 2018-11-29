package com.example.ramil.myuniversity.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.ActivitySignupBinding;
import com.example.ramil.myuniversity.model.User;
import com.example.ramil.myuniversity.model.UsersAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";

    // Firebase vars
    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private String userId;

    private Context mContext;

    public FirebaseUtil(Context context) {
        mContext = context;

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        if (mAuth.getCurrentUser() != null) {
            userId = mAuth.getCurrentUser().getUid();
        }
    }

    /**
     * Register a new user with email and password
     *
     * @param email
     * @param password
     */
    public void registerNewEmail(String email, String password,
                                 final ActivitySignupBinding binding) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            sendVerificationEmail();

                            userId = mAuth.getCurrentUser().getUid();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            // TODO fix progress bar's visibility

                            binding.signUpProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * Send a user a verification email
     */
    public void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        // TODO complete verification method

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "Couldn't send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Add information to the 'users' and 'users_account' database's nodes about new user
     *
     * @param email
     * @param username
     * @param group
     */
    public void addNewUser(String email, String username, String group) {
        User user = new User(userId, email, 1);

        mReference.child(mContext.getString(R.string.db_users))
                .child(userId)
                .setValue(user);

        UsersAccount account = new UsersAccount(username, group, "", "", "");

        mReference.child(mContext.getString(R.string.db_users_account))
                .child(userId)
                .setValue(account);
    }
}
