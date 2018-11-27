package com.example.ramil.myuniversity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramil.myuniversity.databinding.FragmentMoreBinding;

public class MoreFragment extends Fragment {

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMoreBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_more, container, false);

        return binding.getRoot();
    }
}
