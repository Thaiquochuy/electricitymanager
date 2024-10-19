package com.example.electricitymanager.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.electricitymanager.DatabaseHelper;
import com.example.electricitymanager.R;

public class IncreasePriceFragment extends Fragment {

    private EditText edtIncreaseAmount;
    private Button btnIncreasePrice, btnDecreasePrice;
    private DatabaseHelper dbHelper;

    public IncreasePriceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_increase_price, container, false);
        edtIncreaseAmount = view.findViewById(R.id.edtIncreaseAmount);
        btnIncreasePrice = view.findViewById(R.id.btnIncreasePrice);
        btnDecreasePrice = view.findViewById(R.id.btnDecreasePrice);
        dbHelper = new DatabaseHelper(getContext());

        // Xử lý khi nhấn nút tăng giá
        btnIncreasePrice.setOnClickListener(v -> {
            String amountString = edtIncreaseAmount.getText().toString().trim();
            if (!amountString.isEmpty()) {
                int increaseAmount = Integer.parseInt(amountString);
                increaseUnitPrice(increaseAmount); // Gọi phương thức tăng giá
            } else {
                Toast.makeText(getContext(), "Please enter amount: ", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý khi nhấn nút giảm giá
        btnDecreasePrice.setOnClickListener(v -> {
            String amountString = edtIncreaseAmount.getText().toString().trim();
            if (!amountString.isEmpty()) {
                int decreaseAmount = Integer.parseInt(amountString);
                decreaseUnitPrice(decreaseAmount); // Gọi phương thức giảm giá
            } else {
                Toast.makeText(getContext(), "Please enter amount: ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Phương thức cập nhật đơn giá điện tăng lên trong cơ sở dữ liệu
    private void increaseUnitPrice(int increaseAmount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "UPDATE " + DatabaseHelper.TABLE_ELEC_USER_TYPE
                + " SET " + DatabaseHelper.COLUMN_UNIT_PRICE + " = "
                + DatabaseHelper.COLUMN_UNIT_PRICE + " + " + increaseAmount;
        db.execSQL(query);
        db.close();

        Toast.makeText(getContext(), "Increased the unit price of electricity " + increaseAmount, Toast.LENGTH_SHORT).show();
    }

    // Phương thức cập nhật đơn giá điện giảm xuống trong cơ sở dữ liệu
    private void decreaseUnitPrice(int decreaseAmount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "UPDATE " + DatabaseHelper.TABLE_ELEC_USER_TYPE
                + " SET " + DatabaseHelper.COLUMN_UNIT_PRICE + " = "
                + DatabaseHelper.COLUMN_UNIT_PRICE + " - " + decreaseAmount;
        db.execSQL(query);
        db.close();

        Toast.makeText(getContext(), "Decreased the unit price of electricity " + decreaseAmount, Toast.LENGTH_SHORT).show();
    }
}
