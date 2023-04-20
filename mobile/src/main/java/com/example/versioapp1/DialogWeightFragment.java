package com.example.versioapp1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogWeightFragment extends DialogFragment {
    NumberPicker first_number, second_number;
    Button btnOk;
    String firstKg, secondKg;
    SharedPreferences sh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.popup, container, false);

        first_number = view.findViewById(R.id.first_number);
        second_number = view.findViewById(R.id.second_number);
        first_number.setMinValue(1);
        first_number.setMaxValue(450);
        second_number.setMinValue(0);
        second_number.setMaxValue(9);
        first_number.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                firstKg = Integer.toString(first_number.getValue());
            }
        });
        second_number.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                secondKg = Integer.toString(second_number.getValue());
            }
        });
        // sharePreferences
        sh = this.getActivity().getSharedPreferences("userInfoPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        //button okay clicked
        btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myEdit.putString("firstkg", firstKg);
                myEdit.putString("secondKg", secondKg);
                myEdit.commit();
                dismiss();
            }
        });
        return view;

    }
}
