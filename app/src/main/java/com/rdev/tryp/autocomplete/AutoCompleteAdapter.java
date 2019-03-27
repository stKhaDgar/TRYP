package com.rdev.tryp.autocomplete;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.rdev.tryp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AutoCompleteAdapter extends RecyclerView.Adapter {
    public List<AutocompletePrediction> data;

    public interface onPlacePicked {
        void onPlace(AutocompletePrediction prediction);
    }

    private onPlacePicked listener;


    public AutoCompleteAdapter(List<AutocompletePrediction> autocompletePredictions, onPlacePicked listener) {
        this.data = autocompletePredictions;
        this.listener = listener;
    }

    class TextHolder extends RecyclerView.ViewHolder {

        TextView address_tv;
        TextView address_desc_tv;

        TextHolder(@NonNull View itemView) {
            super(itemView);
            this.address_tv = itemView.findViewById(R.id.address_tv);
            this.address_desc_tv = itemView.findViewById(R.id.address_desc_tv);
            itemView.setOnClickListener(view -> listener.onPlace(data.get(getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.autocomplete_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextHolder) holder).address_tv.setText(data.get(position).getPrimaryText(null));
        ((TextHolder) holder).address_desc_tv.setText(data.get(position).getFullText(null));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<AutocompletePrediction> data){
        List<AutocompletePrediction> temp = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            if(!data.get(i).getFullText(null).toString().contains("null")){
                temp.add(data.get(i));
            }
        }
        this.data = temp;
        notifyDataSetChanged();
    }

}
