package com.example.electricitymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.electricitymanager.fragments.EditCustomerFragment;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private boolean showAddress, showUsedNumElectric, showElectricUserType, showPrice;

    public CustomerListAdapter(List<Customer> customerList, boolean showAddress, boolean showUsedNumElectric, boolean showElectricUserType, boolean showPrice) {
        this.customerList = customerList;
        this.showAddress = showAddress;
        this.showUsedNumElectric = showUsedNumElectric;
        this.showElectricUserType = showElectricUserType;
        this.showPrice = showPrice;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.tvCustomerName.setText(customer.getName());
        holder.tvCustomerDob.setText(customer.getDob());
        if (showAddress) {
            holder.tvCustomerAddress.setVisibility(View.VISIBLE);
            holder.tvCustomerAddress.setText(customer.getAddress());
        } else {
            holder.tvCustomerAddress.setVisibility(View.GONE);
        }

        if (showUsedNumElectric) {
            holder.tvElectricNum.setVisibility(View.VISIBLE);
            holder.tvElectricNum.setText(String.valueOf(customer.getUsedElectricNum()));
        } else {
            holder.tvElectricNum.setVisibility(View.GONE);
        }

        if (showElectricUserType) {
            holder.tvElectricType.setVisibility(View.VISIBLE);
            holder.tvElectricType.setText(customer.getElectricTypeName());
        } else {
            holder.tvElectricType.setVisibility(View.GONE);
        }

        if (showPrice) {
            double price = customer.getUsedElectricNum() * customer.getUnitPrice();
            holder.tvUnitPrice.setVisibility(View.VISIBLE);
            holder.tvUnitPrice.setText(String.format("$%.2f", price));
        } else {
            holder.tvUnitPrice.setVisibility(View.GONE);
        }

        // Navigate to EditCustomerFragment when item is clicked
        holder.itemView.setOnClickListener(v -> {
            Fragment editFragment = new EditCustomerFragment(customer);
            FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, editFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvCustomerDob, tvCustomerAddress, tvElectricNum, tvElectricType, tvUnitPrice;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvCustomerDob = itemView.findViewById(R.id.tvCustomerDob);
            tvCustomerAddress = itemView.findViewById(R.id.tvCustomerAddress);
            tvElectricNum = itemView.findViewById(R.id.tvElectricNum);
            tvElectricType = itemView.findViewById(R.id.tvElectricType);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
        }
    }
}
