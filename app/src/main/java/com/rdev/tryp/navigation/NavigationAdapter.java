package com.rdev.tryp.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdev.tryp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NavigationAdapter extends RecyclerView.Adapter {
    interface NavListener {
        void onNavigationClick(int pos);
    }

    NavListener listener;
    ArrayList<String> data;

    public NavigationAdapter(NavListener listener) {
        this.listener = listener;
        data = new ArrayList<>();
        data.add("Main Flow");
        data.add("Map Tryp");
        data.add("Map Detail");
        data.add("Map Next Tryp");
        data.add("Tryp Later");
        data.add("Tryp Car");
        data.add("Confirm Booking");
        data.add("Set location");
        data.add("Connect");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item, parent, false);
        return new DefaultViewHolder(v);
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView itemTv;

        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.nav_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNavigationClick(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DefaultViewHolder) holder).itemTv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
