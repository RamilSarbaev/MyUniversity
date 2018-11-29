package com.example.ramil.myuniversity.otherviews;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";

    private FragmentChatBinding fragmentBinding;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_chat, container, false);

        return fragmentBinding.getRoot();
    }
}
