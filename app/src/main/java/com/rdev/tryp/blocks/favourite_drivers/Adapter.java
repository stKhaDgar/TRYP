package com.rdev.tryp.blocks.favourite_drivers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.rdev.tryp.R;
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<FavoriteDriver> drivers;
    private Context context;
    private FavouriteDriversFragment.OnItemClickListener onItemClickListener;

    public Adapter(List<FavoriteDriver> drivers, Context context, FavouriteDriversFragment.OnItemClickListener onItemClickListener) {
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

        FavoriteDriver model = drivers.get(i);

        viewHolder.title.setText(model.getTitle());
        viewHolder.desc.setText(model.getCategory());
        if (model.isLike()) {
            viewHolder.likeImg.setImageResource(R.drawable.ic_like);
        } else {
            viewHolder.likeImg.setImageResource(R.drawable.ic_like_off);
        }

        if(model.getPhoto() != null){
            viewHolder.pbLoader.setVisibility(View.VISIBLE);
            viewHolder.pbLoader.playAnimation();

            Picasso.get().load(model.getPhoto()).into(viewHolder.imageView, new Callback() {
                @Override
                public void onSuccess() {
                    viewHolder.pbLoader.setVisibility(View.GONE);
                    viewHolder.pbLoader.clearAnimation();
                }

                @Override
                public void onError(Exception e) {
                    viewHolder.pbLoader.setVisibility(View.GONE);
                    viewHolder.pbLoader.clearAnimation();
                    viewHolder.imageView.setImageResource(R.drawable.img);
                }
            }) ;
        } else {
            viewHolder.imageView.setImageResource(R.drawable.img);
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
        LottieAnimationView pbLoader;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            likeImg = itemView.findViewById(R.id.like_img);
            likeLayout = itemView.findViewById(R.id.like_layout);
            likeLayout.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.img);
            pbLoader = itemView.findViewById(R.id.pb_loader);
        }

        @Override
        public void onClick(View v) {
            likeImg.setImageResource(R.drawable.ic_like_off);
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    void delete(int position) {
        drivers.remove(position);
        notifyItemRemoved(position);
    }

    void like(int position) {
        drivers.get(position).setLike(true);
        notifyItemChanged(position);
    }

    void disLike(int position) {
        drivers.get(position).setLike(false);
        notifyItemChanged(position);
    }

}