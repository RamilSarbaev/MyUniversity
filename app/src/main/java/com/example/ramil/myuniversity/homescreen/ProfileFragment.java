package com.example.ramil.myuniversity.homescreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.FragmentProfileBinding;
import com.example.ramil.myuniversity.model.User;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private static final String ARG_USER = "user";

    private FragmentProfileBinding mBinding;

    private User mUser;

    public static ProfileFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setUser(User user) {
        mUser = user;
        mBinding.setUser(mUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getUserFromArgs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile, container, false);

        mBinding.setUser(mUser);

        return mBinding.getRoot();
    }

    private User getUserFromArgs() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getParcelable(ARG_USER);
        } else {
            return null;
        }
    }
}
