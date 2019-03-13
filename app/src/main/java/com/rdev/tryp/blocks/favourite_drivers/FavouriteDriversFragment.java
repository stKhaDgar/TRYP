package com.rdev.tryp.blocks.favourite_drivers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favourite_drivers, container, false);

        initView();
        return root;
    }

    public class OnItemClickListener {
        public void onItemClick(View view, int pos) {
            position = pos;
            switch (view.getId()) {
                case R.id.like_layout:
                    adapter.disLike(position);
                    showConfirm();
                    break;
            }
        }
    }

    public void initView() {
        backBtn = root.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        // Test Data
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Michael Drop", "Tryp Prime", true));
        drivers.add(new Driver("Anna Right", "Tryp", true));
        drivers.add(new Driver("Kate Sun", "Tryp Assist", true));
        drivers.add(new Driver("John Winter", "Tryp", true));

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

    public void showConfirm() {
        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        confirmLayout.setLayoutParams(rel_btn);
    }

    public void hideConfirm() {
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

    public void onPositiveClick() {
        adapter.delete(position);
        hideConfirm();
    }


    public void onNegativeClick() {
        adapter.like(position);
        hideConfirm();
    }
}
