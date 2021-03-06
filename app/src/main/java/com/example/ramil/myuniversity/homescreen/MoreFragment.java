package com.example.ramil.myuniversity.homescreen;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.FragmentMoreBinding;
import com.example.ramil.myuniversity.model.User;
import com.example.ramil.myuniversity.otherviews.ChatActivity;
import com.example.ramil.myuniversity.otherviews.FreelanceActivity;
import com.example.ramil.myuniversity.otherviews.MailActivity;
import com.example.ramil.myuniversity.otherviews.ScheduleActivity;
import com.example.ramil.myuniversity.utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoreFragment extends Fragment {

    private static final String TAG = "MoreFragment";

    private Context mContext;
    private FragmentMoreBinding mBinding;
    private ProfileCallbacks mCallbacks;

    // Firebase vars
    private DatabaseReference mReference;
    private FirebaseUtil mFirebaseUtil;

    private User mUser;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    public interface ProfileCallbacks {
        void onProfileSelected(User user);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ProfileCallbacks) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_more, container, false);
        mBinding.mainLayout.setVisibility(View.INVISIBLE);
        mBinding.setHandlers(new MoreHandlers());

        mContext = getActivity();
        getSetupNavigationView();

        bindUsersProfileLayout();

        return mBinding.getRoot();
    }

    private void bindUsersProfileLayout() {
        mFirebaseUtil = new FirebaseUtil(mContext);

        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = mFirebaseUtil.getUser(dataSnapshot);
                mBinding.setUser(mUser);

                mBinding.moreProgressBar.setVisibility(View.INVISIBLE);
                mBinding.mainLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                mBinding.moreProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getSetupNavigationView() {
        mBinding.moreNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_chat:
                                startActivity(ChatActivity.newIntent(mContext, mUser));
                                return true;
                            case R.id.menu_schedule:
                                startActivity(ScheduleActivity.newIntent(mContext));
                                return true;
                            case R.id.menu_mail:
                                startActivity(MailActivity.newIntent(mContext));
                                return true;
                            case R.id.menu_freelance:
                                startActivity(FreelanceActivity.newIntent(mContext));
                                return true;
                        }

                        return false;
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public class MoreHandlers {

        public void onProfileClicked(View view) {
            mCallbacks.onProfileSelected(mUser);
        }
    }
}
