package com.example.ramil.myuniversity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramil.myuniversity.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentNewsBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_news, container, false);

        binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }
}
