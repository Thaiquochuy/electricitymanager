package com.example.electricitymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.electricitymanager.fragments.EditCustomerFragment;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    private List<Customer> customerList;

    public CustomerListAdapter(List<Customer> customerList) {
        this.customerList = customerList;
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
        holder.tvCustomerAddress.setText(customer.getAddress());
        holder.tvElectricNum.setText(String.valueOf(customer.getUsedElectricNum()));
        holder.tvElectricType.setText(customer.getElectricTypeName());
        holder.tvUnitPrice.setText(String.valueOf(customer.getUnitPrice()));

        holder.itemView.setOnClickListener(v -> openEditCustomerFragment(customer.getId(), v));

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

    private void openEditCustomerFragment(int customerId, View view) {
//        // Create a new instance of EditCustomerFragment and pass customerId
//        EditCustomerFragment editFragment = new EditCustomerFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("customer_id", customerId);
//        editFragment.setArguments(bundle);
//
//        // Retrieve FragmentManager from the Activity context
//        FragmentTransaction transaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, editFragment);  // Replace with actual container ID
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
    }
}
