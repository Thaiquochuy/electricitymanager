package com.example.electricitymanager.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.example.electricitymanager.Customer;
import com.example.electricitymanager.DatabaseHelper;
import com.example.electricitymanager.ElectricUserType;
import com.example.electricitymanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditCustomerFragment extends Fragment {

    private EditText etName, etDob, etAddress, etUsedNumElectric;
    private Spinner spinnerElectricUserType;
    private TextView tvElectricUserTypeName, tvUnitPrice;
    private Button btnEditCustomer, btnDeleteCustomer;
    private DatabaseHelper dbHelper;
    private Customer customer;

    public EditCustomerFragment(Customer customer) {
        this.customer = customer; // Receive customer object to edit
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_customer, container, false);

        // Initialize views
        etName = view.findViewById(R.id.et_name);
        etDob = view.findViewById(R.id.et_dob);
        etAddress = view.findViewById(R.id.et_address);
        etUsedNumElectric = view.findViewById(R.id.et_used_num_electric);
        spinnerElectricUserType = view.findViewById(R.id.spinnerElectricUserType);
        tvElectricUserTypeName = view.findViewById(R.id.tvElectricUserTypeName);
        tvUnitPrice = view.findViewById(R.id.tvUnitPrice);
        btnEditCustomer = view.findViewById(R.id.btn_edit_customer);
        btnDeleteCustomer = view.findViewById(R.id.btn_delete_customer);

        dbHelper = new DatabaseHelper(getContext());
        loadElectricUserTypeData();

        // Load customer data into fields
        loadCustomerData();

        // Date picker for Date of Birth
        etDob.setOnClickListener(v -> showDatePicker(etDob));

        // Update customer button click
        btnEditCustomer.setOnClickListener(v -> {
            if (validateInputs()) {
                updateCustomerInDatabase();
            }
        });

        // Delete customer button click
        btnDeleteCustomer.setOnClickListener(v -> {
            // Show confirmation dialog before deleting
            new androidx.appcompat.app.AlertDialog.Builder(getContext())
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this customer?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        deleteCustomerFromDatabase();
                    })
                    .setNegativeButton("No", null)
                    .show();
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

    private void loadCustomerData() {
        etName.setText(customer.getName());
        etDob.setText(customer.getDob());
        etAddress.setText(customer.getAddress());
        etUsedNumElectric.setText(String.valueOf(customer.getUsedElectricNum()));
    }

    private boolean validateInputs() {
        if (etName.getText().toString().isEmpty() || etDob.getText().toString().isEmpty() ||
                etAddress.getText().toString().isEmpty() || etUsedNumElectric.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateCustomerInDatabase() {
        String name = etName.getText().toString();
        String dob = etDob.getText().toString();
        String address = etAddress.getText().toString();
        int usedNumElectric = Integer.parseInt(etUsedNumElectric.getText().toString());
        int elecUserTypeId = ((ElectricUserType) spinnerElectricUserType.getSelectedItem()).getId();

        dbHelper.updateCustomer(customer.getId(), name, dob, address, usedNumElectric, elecUserTypeId);
        Toast.makeText(getContext(), "Customer updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void deleteCustomerFromDatabase() {
        dbHelper.deleteCustomer(customer.getId());
        Toast.makeText(getContext(), "Customer deleted successfully", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
