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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rdev.tryp.blocks.invite_friends.InviteFriendsFragment;
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase;
import com.rdev.tryp.model.RealmUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapNextTrip extends Fragment implements View.OnClickListener {

    private ImageButton locationBtn;
    private ImageView smallImage, shareBtn;
    private RelativeLayout nextBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.next_fragment, container, false);

        initView(v);

        return v;
    }

    private void initView(View v) {
        smallImage = v.findViewById(R.id.small_image);
        locationBtn = v.findViewById(R.id.location_btn);
        nextBtn = v.findViewById(R.id.next_layout_btn);
        shareBtn = v.findViewById(R.id.share_btn);

        locationBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);

        setSmallImage(v);
    }

    private void setSmallImage(View v) {

        String img = new RealmUtils(v.getContext(), null).getCurrentUser().getImage();

        if(img != null && !img.equals("null")){
            Picasso.get().load(img).into(smallImage);
        } else {
            int height = 100;
            int width = 100;
            BitmapDrawable bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.small_person);
            Bitmap b = bitmapDraw.getBitmap();
            Bitmap bitmap = Bitmap.createScaledBitmap(b, width, height, false);
            smallImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_btn:
                ((ContentActivity) Objects.requireNonNull(getActivity())).zoomToCurrentLocation();
                break;
            case R.id.next_layout_btn:
                ((ContentActivity) Objects.requireNonNull(getActivity())).showDirectionPicker(null);
                break;
            case R.id.share_btn:
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .add(R.id.screenContainer, new InviteFriendsFragment())
                        .addToBackStack("share")
                        .commit();
                break;
        }
    }
}
