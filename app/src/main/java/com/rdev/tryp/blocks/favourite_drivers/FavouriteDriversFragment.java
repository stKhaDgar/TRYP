package com.rdev.tryp.blocks.favourite_drivers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.firebaseDatabase.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;
import afu.org.checkerframework.checker.nullness.qual.NonNull;
import afu.org.checkerframework.checker.nullness.qual.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteDriversFragment extends Fragment implements View.OnClickListener {

    private View root;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private RelativeLayout confirmLayout;
    private int position;
    private ImageView backBtn;
    final List<FavoriteDriver> drivers = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favourite_drivers, container, false);

        initView();
        return root;
    }

    class OnItemClickListener {
        void onItemClick(View view, int pos) {
            position = pos;
            switch (view.getId()) {
                case R.id.like_layout:
                    adapter.disLike(position);
                    showConfirm();
                    break;
            }
        }
    }

    private void initView() {
        backBtn = root.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        ((ContentActivity) getActivity()).database.getFavoritedDrivers(driver -> {
            if(!DatabaseUtils.INSTANCE.arrayContainsDriverId(drivers, driver.getDriverId())){

                FavoriteDriver temp = new FavoriteDriver(driver.getFirstName(), driver.getLastName());
                temp.setId(driver.getDriverId());
                temp.setCategory(driver.getCategory());
                temp.setPhoto(driver.getImage());

                drivers.add(temp);
                adapter.notifyItemInserted(drivers.size()-1);

            }
        });

        OnItemClickListener listener = new OnItemClickListener();
        adapter = new Adapter(drivers, getContext(), listener);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        View confirmButton = root.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);

        confirmLayout = root.findViewById(R.id.confirm_layout);
        confirmLayout.setOnClickListener(this);
        hideConfirm();
    }

    private void showConfirm() {
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        confirmLayout.setLayoutParams(rel_btn);
    }

    private void hideConfirm() {
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        confirmLayout.setLayoutParams(rel_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_button:
                onPositiveClick();
                break;
            case R.id.confirm_layout:
                onNegativeClick();
                break;
            case R.id.back_btn:
                ((ContentActivity) getActivity()).goHome();
                break;
        }
    }

    private void onPositiveClick() {
        ((ContentActivity) getActivity()).database.addDriverToFavorite(drivers.get(position).getId(), false);
        adapter.delete(position);
        hideConfirm();
    }

    private void onNegativeClick() {
        adapter.like(position);
        hideConfirm();
    }

}