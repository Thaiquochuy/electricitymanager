package com.example.electricitymanager;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;

    // Constructor chỉ với customerList
    public CustomerAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.tvCustomerName.setText("Customer Name: " + customer.getName());
        holder.tvCustomerDob.setText("DOB: " + customer.getDob());
        holder.tvCustomerAddress.setText("Address: " + customer.getAddress());
        holder.tvUsedElectric.setText("Electric Num: " + customer.getUsedElectricNum());
        holder.tvElectricType.setText("Electric Type: " + customer.getElectricTypeName());
        holder.tvUnitPrice.setText("Unit Price: " + customer.getUnitPrice());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvCustomerDob, tvCustomerAddress, tvUsedElectric, tvElectricType, tvUnitPrice;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvCustomerDob = itemView.findViewById(R.id.tvCustomerDob);
            tvCustomerAddress = itemView.findViewById(R.id.tvCustomerAddress);
            tvUsedElectric = itemView.findViewById(R.id.tvUsedElectric);
            tvElectricType = itemView.findViewById(R.id.tvElectricType);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
        }
    }
}
