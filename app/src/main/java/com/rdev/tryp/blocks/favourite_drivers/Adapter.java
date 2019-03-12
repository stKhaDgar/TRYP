package com.rdev.tryp.blocks.favourite_drivers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdev.tryp.R;

import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Driver> drivers;
    private Context context;
    private FavouriteDriversFragment.OnItemClickListener onItemClickListener;

    public Adapter(List<Driver> drivers, Context context, FavouriteDriversFragment.OnItemClickListener onItemClickListener) {
        this.drivers = drivers;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite_driver, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MyViewHolder viewHolder = myViewHolder;

        Driver model = drivers.get(i);

        viewHolder.title.setText(model.getTitle());
        viewHolder.desc.setText(model.getDescription());
        if (model.isLike()) {
            viewHolder.likeImg.setImageResource(R.drawable.ic_like);
        } else {
            viewHolder.likeImg.setImageResource(R.drawable.ic_like_off);
        }
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, desc;
        ImageView imageView, likeImg;
        FrameLayout likeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            likeImg = itemView.findViewById(R.id.like_img);
            likeLayout = itemView.findViewById(R.id.like_layout);
            likeLayout.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.img);

        }

        @Override
        public void onClick(View v) {
            likeImg.setImageResource(R.drawable.ic_like_off);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void delete(int position) {
        drivers.remove(position);
        notifyItemRemoved(position);
    }

    public void like(int position) {
        drivers.get(position).setLike(true);
        notifyItemChanged(position);
    }

    public void disLike(int position) {
        drivers.get(position).setLike(false);
        notifyItemChanged(position);
    }
}
