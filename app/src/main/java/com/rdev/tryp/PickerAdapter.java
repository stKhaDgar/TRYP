package com.rdev.tryp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.rdev.tryp.utils.Utils.getCountryName;
import static com.rdev.tryp.utils.Utils.getDialingCode;

class PickerAdapter extends RecyclerView.Adapter {
    String[] data;
    SelectCountryListener listener;

    PickerAdapter(String[] mTestArray, SelectCountryListener listener) {
        data = mTestArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.phone_item, parent, false));
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView countryTv;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            countryTv = itemView.findViewById(R.id.country_tv);
            itemView.setOnClickListener(view -> listener.onSelect(data[getAdapterPosition()]));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String country = INSTANCE.getCountryName(data[position]) + " (+" + INSTANCE.getDialingCode(data[position]) + ")";
        ((ItemHolder) holder).countryTv.setText(country);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
