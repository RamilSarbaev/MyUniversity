package com.example.ramil.myuniversity.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerUtil {

    @BindingAdapter(value = {"selectedGender", "selectedGenderAttrChanged"}, requireAll = false)
    public static void getIndexGenderForSpinner(Spinner spinner,
                                                String newSelectedGender,
                                                final InverseBindingListener newGenderAttrChanged) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newGenderAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        if (newSelectedGender != null) {
            ArrayAdapter<String> adapter = ((ArrayAdapter<String>) spinner.getAdapter());
            int position = adapter.getPosition(newSelectedGender);
            spinner.setSelection(position);
        }
    }

    @InverseBindingAdapter(attribute = "selectedGender", event = "selectedGenderAttrChanged")
    public static String setValueGender(Spinner spinner) {
        return (String) spinner.getSelectedItem();
    }
}
