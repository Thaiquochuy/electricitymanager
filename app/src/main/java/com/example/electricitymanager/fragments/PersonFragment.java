package com.example.electricitymanager.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electricitymanager.DatabaseHelper;
import com.example.electricitymanager.ElectricUserType;
import com.example.electricitymanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonFragment extends Fragment {

    private EditText etName, etDob, etAddress, etUsedNumElectric;
    private Spinner spinnerElectricUserType;
    private TextView tvElectricUserTypeName, tvUnitPrice;
    private Button btnAddCustomer;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        // Initialize views
        etName = view.findViewById(R.id.et_name);
        etDob = view.findViewById(R.id.et_dob);
        etAddress = view.findViewById(R.id.et_address);
        etUsedNumElectric = view.findViewById(R.id.et_used_num_electric);
        spinnerElectricUserType = view.findViewById(R.id.spinnerElectricUserType);
        tvElectricUserTypeName = view.findViewById(R.id.tvElectricUserTypeName);
        tvUnitPrice = view.findViewById(R.id.tvUnitPrice);
        btnAddCustomer = view.findViewById(R.id.btn_add_customer);

        dbHelper = new DatabaseHelper(getContext());
        loadElectricUserTypeData();

        // Date picker for Date of Birth
        etDob.setOnClickListener(v -> showDatePicker(etDob));

        btnAddCustomer.setOnClickListener(v -> {
            if (validateInputs()) {
                addCustomerToDatabase();
            }
        });

        return view;
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String dob = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editText.setText(dob);
                }, year, month, day);
        datePickerDialog.show();
    }

    @SuppressLint("Range")
    private void loadElectricUserTypeData() {
        List<ElectricUserType> electricUserTypes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ELEC_USER_TYPE, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ELEC_USER_TYPE_NAME));
                int unitPrice = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_PRICE));

                // Create an ElectricUserType object and add it to the list
                ElectricUserType userType = new ElectricUserType(id, name, unitPrice);
                electricUserTypes.add(userType);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Create an adapter for the electric user types
        ArrayAdapter<ElectricUserType> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, electricUserTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerElectricUserType.setAdapter(adapter);

        // Set the item selected listener
        spinnerElectricUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ElectricUserType selectedUserType = (ElectricUserType) parent.getItemAtPosition(position);
                tvElectricUserTypeName.setText(selectedUserType.getName());
                tvUnitPrice.setText(String.valueOf(selectedUserType.getUnitPrice()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvElectricUserTypeName.setText("");
                tvUnitPrice.setText("");
            }
        });
    }

    private boolean validateInputs() {
        String name = etName.getText().toString();
        String dob = etDob.getText().toString();
        String address = etAddress.getText().toString();
        String usedNumElectricStr = etUsedNumElectric.getText().toString();

        // Kiểm tra các trường không được để trống
        if (name.isEmpty() || dob.isEmpty() || address.isEmpty() || usedNumElectricStr.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra không cho nhập số vào trường tên
        if (!name.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(getContext(), "Name should not contain numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra giá trị số điện đã sử dụng có phải là số hợp lệ
        int usedNumElectric;
        try {
            usedNumElectric = Integer.parseInt(usedNumElectricStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid number for electric usage", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra không cho nhập số âm vào số điện đã sử dụng
        if (usedNumElectric < 0) {
            Toast.makeText(getContext(), "Electric usage cannot be negative", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void addCustomerToDatabase() {
        String name = etName.getText().toString();
        String dob = etDob.getText().toString();
        String address = etAddress.getText().toString();
        int usedNumElectric = Integer.parseInt(etUsedNumElectric.getText().toString());
        int elecUserTypeId = ((ElectricUserType) spinnerElectricUserType.getSelectedItem()).getId();

        long result = dbHelper.addCustomer(name, dob, address, usedNumElectric, elecUserTypeId);

        if (result != -1) {
            Toast.makeText(getContext(), "Customer added successfully", Toast.LENGTH_SHORT).show();
            clearFormInputs();
        } else {
            Toast.makeText(getContext(), "Error adding customer", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFormInputs() {
        etName.setText("");  // Clear the name field
        etDob.setText("");   // Clear the date of birth field
        etAddress.setText("");   // Clear the address field
        etUsedNumElectric.setText(""); // Clear the used number of electricity field
        spinnerElectricUserType.setSelection(0); // Reset the spinner to the first item
        tvElectricUserTypeName.setText(""); // Clear the electric user type name
        tvUnitPrice.setText(""); // Clear the unit price
    }
}
