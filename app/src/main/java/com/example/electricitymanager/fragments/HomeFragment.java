package com.example.electricitymanager.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.electricitymanager.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Tạo View cho Fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Tìm nút trong layout
        Button btnCustomer = view.findViewById(R.id.btnCustomer);
        Button btnListCustomers = view.findViewById(R.id.btnListCustomers);
        Button btnElectricIndex = view.findViewById(R.id.btnElectricIndex);

        // Thiết lập lắng nghe sự kiện click cho nút
        btnCustomer.setOnClickListener(v -> navigateToPersonFragment());
        btnListCustomers.setOnClickListener(v -> navigateToMenuFragment());
        btnElectricIndex.setOnClickListener(v -> navigatetoElectricFragement());

        return view;
    }

    private void navigateToPersonFragment() {
        PersonFragment personFragment = new PersonFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, personFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToMenuFragment() {
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, menuFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigatetoElectricFragement() {
        IncreasePriceFragment increasePriceFragment = new IncreasePriceFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, increasePriceFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
