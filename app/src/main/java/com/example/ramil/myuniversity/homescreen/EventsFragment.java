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
import com.example.ramil.myuniversity.databinding.FragmentEventsBinding;

public class EventsFragment extends Fragment {

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentEventsBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_events, container, false);

        return binding.getRoot();
    }
}
