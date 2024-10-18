package com.example.electricitymanager.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electricitymanager.Customer;
import com.example.electricitymanager.CustomerAdapter;
import com.example.electricitymanager.DatabaseHelper;
import com.example.electricitymanager.ElectricUserType;
import com.example.electricitymanager.R;

import java.util.ArrayList;
import java.util.List;

public class SearchCustomerFragment extends Fragment {
    private EditText edtSearchTerm;
    private Spinner spinnerSearchBy;
    private Button btnSearchCustomer;
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewCustomers;
    private CustomerAdapter customerAdapter;
    private List<Customer> customerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Tạo View cho Fragment
        View view = inflater.inflate(R.layout.fragment_search_customer, container, false);
        edtSearchTerm = view.findViewById(R.id.edtSearchTerm);
        spinnerSearchBy = view.findViewById(R.id.spinnerSearchBy);
        btnSearchCustomer = view.findViewById(R.id.btnSearchCustomer);
        recyclerViewCustomers = view.findViewById(R.id.recyclerViewCustomers);
        dbHelper = new DatabaseHelper(getContext());

        // Khởi tạo RecyclerView
        customerList = new ArrayList<>();
        customerAdapter = new CustomerAdapter(customerList);
        recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCustomers.setAdapter(customerAdapter);

        btnSearchCustomer.setOnClickListener(v -> {
            String searchTerm = edtSearchTerm.getText().toString().trim();
            String searchBy = spinnerSearchBy.getSelectedItem().toString();

            if (!searchTerm.isEmpty()) {
                searchCustomer(searchTerm, searchBy); // Gọi phương thức tìm kiếm
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập từ khóa", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchCustomer(String searchTerm, String searchBy) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            if (searchBy.equals("TÊN")) {
                cursor = db.query(DatabaseHelper.TABLE_CUSTOMER, null,
                        DatabaseHelper.COLUMN_NAME + " LIKE ?",
                        new String[]{"%" + searchTerm + "%"}, null, null, null);
            } else {
                cursor = db.query(DatabaseHelper.TABLE_CUSTOMER, null,
                        DatabaseHelper.COLUMN_ADDRESS + " LIKE ?",
                        new String[]{"%" + searchTerm + "%"}, null, null, null);
            }

            customerList.clear(); // Clear the list before adding new results

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int customerId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)); // ID
                    String customerName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)); // Name
                    String customerDob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_YYYYMM)); // DOB
                    String customerAddress = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS)); // Address
                    int usedElectricNum = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USED_NUM_ELECTRIC)); // Used Electric Number

                    // Fetch the electric user type name and unit price separately
                    int electricUserTypeId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ELEC_USER_TYPE_ID));
                    ElectricUserType electricUserType = dbHelper.getElectricUserTypeById(electricUserTypeId); // Create this method in DatabaseHelper

                    String electricTypeName = electricUserType != null ? electricUserType.getName() : "Unknown"; // Handle null
                    double unitPrice = electricUserType != null ? electricUserType.getUnitPrice() : 0; // Handle null

                    // Add customer to the list
                    customerList.add(new Customer(customerId, customerName, customerDob, customerAddress, usedElectricNum, electricTypeName, unitPrice));
                } while (cursor.moveToNext());

                recyclerViewCustomers.setVisibility(View.VISIBLE); // Show RecyclerView
            } else {
                Toast.makeText(getContext(), "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                recyclerViewCustomers.setVisibility(View.GONE); // Hide RecyclerView if no results
            }

            customerAdapter.notifyDataSetChanged(); // Notify adapter of data changes
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            Toast.makeText(getContext(), "Lỗi khi tìm kiếm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close(); // Always close the cursor
            }
            db.close(); // Close the database
        }
    }
}
