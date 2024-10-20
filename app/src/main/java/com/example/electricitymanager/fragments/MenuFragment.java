package com.example.electricitymanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electricitymanager.Customer;
import com.example.electricitymanager.CustomerListAdapter;
import com.example.electricitymanager.DatabaseHelper;
import com.example.electricitymanager.R;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private RecyclerView recyclerViewCustomers;
    private CustomerListAdapter customerListAdapter;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Ensure you are inflating the correct layout that contains the RecyclerView
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Use androidx Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu); // Inflate the menu

        // Handle menu item click
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_settings) {
                openSettingsFragment();
                return true;
            }
            return false;
        });

        recyclerViewCustomers = view.findViewById(R.id.recyclerViewCustomers);
        recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());

        // Load customers from the database
        List<Customer> customerList = loadCustomersFromDatabase();

        // Load settings from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean showAddress = sharedPreferences.getBoolean("show_address", true);
        boolean showUsedNumElectric = sharedPreferences.getBoolean("show_used_num_electric", true);
        boolean showElectricUserType = sharedPreferences.getBoolean("show_electric_user_type", true);
        boolean showPrice = sharedPreferences.getBoolean("show_price", true);

        // Set adapter for RecyclerView and pass the visibility preferences
        customerListAdapter = new CustomerListAdapter(customerList, showAddress, showUsedNumElectric, showElectricUserType, showPrice);
        recyclerViewCustomers.setAdapter(customerListAdapter);

        return view;
    }

    private void openSettingsFragment() {
        Fragment settingsFragment = new SettingFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, settingsFragment); // Replace the container with the settings fragment
        transaction.addToBackStack(null); // Add to back stack so user can navigate back
        transaction.commit();
    }

    private List<Customer> loadCustomersFromDatabase() {
        List<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT c.id, c.name, c.yyyymm, c.address, c.used_num_electric, e.elec_user_type_name, e.unit_price " +
                "FROM " + DatabaseHelper.TABLE_CUSTOMER + " c " +
                "JOIN " + DatabaseHelper.TABLE_ELEC_USER_TYPE + " e " +
                "ON c.elec_user_type_id = e.id";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String dob = cursor.getString(cursor.getColumnIndexOrThrow("yyyymm"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                int usedElectricNum = cursor.getInt(cursor.getColumnIndexOrThrow("used_num_electric"));
                String electricTypeName = cursor.getString(cursor.getColumnIndexOrThrow("elec_user_type_name"));
                double unitPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("unit_price"));

                Customer customer = new Customer(id, name, dob, address, usedElectricNum, electricTypeName, unitPrice);
                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return customerList;
    }
}
