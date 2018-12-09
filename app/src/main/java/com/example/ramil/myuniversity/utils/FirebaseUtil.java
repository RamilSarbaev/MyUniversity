package com.example.ramil.myuniversity.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ramil.myuniversity.databinding.ActivitySignupBinding;
import com.example.ramil.myuniversity.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";
    private static final String USERS_CHILD = "users";
    private static final String PROFILE_PHOTO_CHILD = "profile_photo";

    // Firebase vars
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private String mUserId;

    private Context mContext;

    public FirebaseUtil(Context context) {
        mContext = context;

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        if (mAuth.getCurrentUser() != null) {
            mUserId = mAuth.getCurrentUser().getUid();
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

                            mUserId = mAuth.getCurrentUser().getUid();
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
        User user = new User(mUserId, username, email, group);

        mReference.child(USERS_CHILD)
                .child(mUserId)
                .setValue(user);
    }

    /**
     * Updates user's data in database: users node
     *
     * @param user
     */
    public void updateUsersData(User user) {
        mReference.child(USERS_CHILD)
                .child(mUserId)
                .setValue(user);
    }

    /**
     * Retrieves the profile for the user currently logged in
     * database: users node
     *
     * @param dataSnapshot
     * @return
     */
    public User getUser(DataSnapshot dataSnapshot) {
        return dataSnapshot.child(USERS_CHILD)
                .child(mUserId)
                .getValue(User.class);
    }

    /**
     * Retrieves user with current uid
     *
     * @param dataSnapshot
     * @param uid
     * @return
     */
    public User getUser(DataSnapshot dataSnapshot, String uid) {
        return dataSnapshot.child(USERS_CHILD)
                .child(uid)
                .getValue(User.class);
    }

    /**
     * Upload user's photo in Firebase Storage
     *
     * @param uri
     */
    public void putImageInStorage(Uri uri, final User user) {
        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference(mUserId)
                .child(PROFILE_PHOTO_CHILD)
                .child(uri.getLastPathSegment());

        UploadTask uploadTask = storageReference.putFile(uri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw  task.getException();
                }

                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener((Activity) mContext, new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // TODO add RX for retrieve path in EditProfileActivity
                    String path = task.getResult().toString();
                    user.setPhoto(path);
                } else {
                    Log.w(TAG, "Image upload task wasn't successful.", task.getException());
                }
            }
        });
    }
}
