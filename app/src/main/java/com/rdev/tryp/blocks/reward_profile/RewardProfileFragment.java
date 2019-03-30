package com.rdev.tryp.blocks.reward_profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.firebaseDatabase.model.Client;
import com.rdev.tryp.model.RealmCallback;
import com.rdev.tryp.model.RealmUtils;
import com.rdev.tryp.model.login_response.Users;
import com.rdev.tryp.storageDatabase.OnFileLoadCallback;
import com.rdev.tryp.storageDatabase.utils.StorageUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import afu.org.checkerframework.checker.nullness.qual.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class RewardProfileFragment extends Fragment implements View.OnClickListener {
    private static final int PICK_IMAGE = 958;

    private ImageView mainPhoto;
    private ImageView btnSettings;
    private Button btnSave;
    private ImageButton btnCancel;
    private View btnChangePhoto;
    private CardView editLayout;
    private CardView mainLayout;
    private ConstraintLayout photoLayout;
    private LinearLayout mainLayout2;
    private ConstraintLayout loadScreen;
    private LottieAnimationView pbLoader;
    private EditText etFirstName;
    private EditText etLastName;

    private TextView tvName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reward_profile, container, false);

        ImageView fab = root.findViewById(R.id.back_btn);
        fab.setOnClickListener(this);
        CardView cardView = root.findViewById(R.id.top_card_view);
        cardView.setBackgroundResource(R.drawable.card_view_bg);
        loadScreen = root.findViewById(R.id.load_screen);
        pbLoader = root.findViewById(R.id.pb_loader);
        editLayout = root.findViewById(R.id.edit_layout);
        mainLayout = root.findViewById(R.id.main_layout);
        mainLayout2 = root.findViewById(R.id.other_main_layout);
        photoLayout = root.findViewById(R.id.layout_photo);
        tvName = root.findViewById(R.id.tvName);
        etFirstName = root.findViewById(R.id.etFirstName);
        etLastName = root.findViewById(R.id.etLastName);

        CardView rewardPoints = root.findViewById(R.id.rewards_points_card_view);
        rewardPoints.setOnClickListener(this);
        CardView credits = root.findViewById(R.id.credits_card_view);
        credits.setOnClickListener(this);

        btnSettings = root.findViewById(R.id.settings_img);
        btnSettings.setOnClickListener(this);

        btnChangePhoto = root.findViewById(R.id.btnChangePhoto);
        btnChangePhoto.setOnClickListener(this);

        btnSave = root.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnCancel = root.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        initUI(root);

        return root;
    }

    private void initUI(View v){
        Users user = new RealmUtils(v.getContext(), null).getCurrentUser();
        String img = user.getImage();
        if(img != null && !img.equals("null")){
            mainPhoto = v.findViewById(R.id.main_img);
            Picasso.get().load(img).into(mainPhoto);
        }

        String tempName = user.getFirstName() + " " + user.getLastName();
        tvName.setText(tempName);

        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                ((ContentActivity) getActivity()).goHome();
                break;
            case R.id.credits_card_view:
                Toast.makeText(getContext(), "clicked credits", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rewards_points_card_view:
                ((ContentActivity) getActivity()).startFragment(ContentActivity.TYPE_REWARD_POINTS);
                break;
            case R.id.settings_img:
                initEditor(true);
                break;
            case R.id.btnCancel:
                initEditor(false);
                break;
            case R.id.btnChangePhoto:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
        }
    }

    private void initEditor(boolean isShow){
        ImageView iconAddPhoto = getView().findViewById(R.id.icon_add_photo);

        if(isShow){
            btnSettings.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            mainLayout2.setVisibility(View.INVISIBLE);
            btnChangePhoto.setVisibility(View.VISIBLE);
            iconAddPhoto.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            mainPhoto.setScaleX(1.1F);
            mainPhoto.setScaleY(1.1F);
            btnSave.setOnClickListener(v -> pushOnlyName());
            photoLayout.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.height200);
            photoLayout.requestLayout();
        } else {
            btnSettings.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
            mainLayout2.setVisibility(View.VISIBLE);
            btnChangePhoto.setVisibility(View.INVISIBLE);
            iconAddPhoto.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.INVISIBLE);
            editLayout.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
            mainPhoto.setScaleX(1.0F);
            mainPhoto.setScaleY(1.0F);
            photoLayout.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.height354);
            photoLayout.requestLayout();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            try{
                Uri uriImage = data.getData();
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uriImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                mainPhoto.setImageBitmap(bitmap);

                btnSave.setOnClickListener(v -> {
                    loadScreen.setVisibility(View.VISIBLE);
                    pbLoader.playAnimation();

                    new StorageUtils().pushPhoto(b, new OnFileLoadCallback() {
                        @Override
                        public void dataUpdated(@NotNull String url) {
                            Client client = new Client();
                            client.setId(new RealmUtils(null, null).getCurrentUser().getUserId().toString());
                            client.setPhoto(url);
                            client.setFirst_name(etFirstName.getText().toString());
                            client.setLast_name(etLastName.getText().toString());
                            new RealmUtils(getContext(), new RealmCallback() {
                                @Override
                                public void dataUpdated() {
                                    Users user = new RealmUtils(null, null).getCurrentUser();
                                    ((ContentActivity) getActivity()).database.updateUser(user, null, null);
                                    initEditor(false);
                                    initUI(getView());

                                    ((ContentActivity) getActivity()).updateAvatar();

                                    loadScreen.setVisibility(View.INVISIBLE);
                                    pbLoader.clearAnimation();
                                }

                                @Override
                                public void error() {
                                    loadScreen.setVisibility(View.INVISIBLE);
                                    pbLoader.clearAnimation();
                                }
                            }).updateUser(client);
                        }

                        @Override
                        public void error(@NotNull Exception e) {
                            loadScreen.setVisibility(View.INVISIBLE);
                            pbLoader.clearAnimation();
                        }
                    });
                });
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getView().getContext(), "Image was not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pushOnlyName(){
        Client client = new Client();
        client.setId(new RealmUtils(null, null).getCurrentUser().getUserId().toString());
        client.setFirst_name(etFirstName.getText().toString());
        client.setLast_name(etLastName.getText().toString());
        new RealmUtils(getContext(), new RealmCallback() {
            @Override
            public void dataUpdated() {
                Users user = new RealmUtils(null, null).getCurrentUser();
                ((ContentActivity) getActivity()).database.updateUser(user, null, null);
                initEditor(false);
                initUI(getView());

                ((ContentActivity) getActivity()).updateAvatar();

                loadScreen.setVisibility(View.INVISIBLE);
                pbLoader.clearAnimation();
            }

            @Override
            public void error() {
                loadScreen.setVisibility(View.INVISIBLE);
                pbLoader.clearAnimation();
            }
        }).updateUser(client);
    }

}