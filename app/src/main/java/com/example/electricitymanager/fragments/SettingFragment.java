package com.example.electricitymanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.electricitymanager.R;

public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Đặt chiều rộng và chiều cao của fragment
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;  // Chiều rộng tự động vừa đủ
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Chiều cao tự động vừa đủ
        view.setLayoutParams(layoutParams);

        // Initialize switches and load settings
        initializeSwitches(view);

        return view;
    }

    private void initializeSwitches(View view) {
        Switch switchShowAddress = view.findViewById(R.id.switch_show_address);
        Switch switchShowUsedNumElectric = view.findViewById(R.id.switch_show_used_num_electric);
        Switch switchShowElectricUserType = view.findViewById(R.id.switch_show_elec_user_type);
        Switch switchShowPrice = view.findViewById(R.id.switch_show_price);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);

        // Load saved preferences
        loadSettings(sharedPreferences, switchShowAddress, switchShowUsedNumElectric, switchShowElectricUserType, switchShowPrice);

        // Set listeners to save changes to preferences
        setSwitchListeners(sharedPreferences, switchShowAddress, switchShowUsedNumElectric, switchShowElectricUserType, switchShowPrice);
    }

    // Load saved settings into the switches
    private void loadSettings(SharedPreferences sharedPreferences, Switch switchShowAddress, Switch switchShowUsedNumElectric, Switch switchShowElectricUserType, Switch switchShowPrice) {
        switchShowAddress.setChecked(sharedPreferences.getBoolean("show_address", true));
        switchShowUsedNumElectric.setChecked(sharedPreferences.getBoolean("show_used_num_electric", true));
        switchShowElectricUserType.setChecked(sharedPreferences.getBoolean("show_electric_user_type", true));
        switchShowPrice.setChecked(sharedPreferences.getBoolean("show_price", true));
    }

    // Set listeners to save the switch state in SharedPreferences
    private void setSwitchListeners(SharedPreferences sharedPreferences, Switch switchShowAddress, Switch switchShowUsedNumElectric, Switch switchShowElectricUserType, Switch switchShowPrice) {
        switchShowAddress.setOnCheckedChangeListener((buttonView, isChecked) -> saveSetting(sharedPreferences, "show_address", isChecked));
        switchShowUsedNumElectric.setOnCheckedChangeListener((buttonView, isChecked) -> saveSetting(sharedPreferences, "show_used_num_electric", isChecked));
        switchShowElectricUserType.setOnCheckedChangeListener((buttonView, isChecked) -> saveSetting(sharedPreferences, "show_electric_user_type", isChecked));
        switchShowPrice.setOnCheckedChangeListener((buttonView, isChecked) -> saveSetting(sharedPreferences, "show_price", isChecked));
    }

    // Save setting in SharedPreferences
    private void saveSetting(SharedPreferences sharedPreferences, String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
