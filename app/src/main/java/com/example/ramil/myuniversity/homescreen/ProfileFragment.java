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
import com.example.ramil.myuniversity.model.UsersAccount;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private static final String ARG_USERS_ACCOUNT = "users_account";

    private FragmentProfileBinding mBinding;

    private UsersAccount mUsersAccount;

    public static ProfileFragment newInstance(UsersAccount account) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USERS_ACCOUNT, account);

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsersAccount = getUsersAccountFromArgs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile, container, false);

        mBinding.setUsersAccount(mUsersAccount);

        return mBinding.getRoot();
    }

    private UsersAccount getUsersAccountFromArgs() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getParcelable(ARG_USERS_ACCOUNT);
        } else {
            return null;
        }
    }
}
