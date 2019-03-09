package com.rdev.tryp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MapNextTrip extends Fragment {
    CardView were_to;
    ImageButton location_btn;
    ImageButton back_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.next_fragment, container, false);


        ImageView imageView = v.findViewById(R.id.small_image);

        int height = 70;
        int width = 70;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.small_person);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap bitmap = Bitmap.createScaledBitmap(b, width, height, false);

        imageView.setImageBitmap(bitmap);

        were_to = v.findViewById(R.id.were_to);
        were_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ContentActivity) getActivity()).pickAdress();
            }
        });
        location_btn = v.findViewById(R.id.location_btn);
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ContentActivity) getActivity()).zoomToCurrentLocation();
            }
        });
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ContentActivity) getActivity()).popBackStack();
            }
        });
        return v;
    }
}
