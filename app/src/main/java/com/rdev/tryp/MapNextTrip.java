package com.rdev.tryp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapNextTrip extends Fragment implements View.OnClickListener {

    private View v;
    private ImageButton locationBtn;
    private ImageView smallImage;
    private RelativeLayout nextBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.next_fragment, container, false);

        initView();

        return v;
    }

    public void initView() {
        smallImage = v.findViewById(R.id.small_image);
        locationBtn = v.findViewById(R.id.location_btn);
        nextBtn = v.findViewById(R.id.next_layout_btn);

        locationBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        setSmallImage();
    }

    public void setSmallImage() {    //TODO: set small image by user image
        int height = 70;
        int width = 70;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.small_person);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap bitmap = Bitmap.createScaledBitmap(b, width, height, false);
        smallImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_btn:
                ((ContentActivity) getActivity()).zoomToCurrentLocation();
                break;
            case R.id.next_layout_btn:
                ((ContentActivity) getActivity()).showDirectionPicker(null);
        }
    }
}
