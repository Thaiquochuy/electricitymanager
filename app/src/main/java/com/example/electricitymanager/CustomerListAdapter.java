package com.example.electricitymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    private final List<Customer> customerList;
    private OnCustomerClickListener onCustomerClickListener;

    public interface OnCustomerClickListener {
        void onCustomerClick(Customer customer);
    }

//    public CustomerListAdapter(List<Customer> customerList) {
//        this.customerList = customerList;
//    }

    public CustomerListAdapter(List<Customer> customerList) {
        this.customerList = customerList;
        this.onCustomerClickListener = null;
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
