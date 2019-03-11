package com.rdev.tryp.forme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.autocomplete.AutoCompleteAdapter;
import com.rdev.tryp.model.TripPlace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import afu.org.checkerframework.checker.nullness.qual.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment implements AutoCompleteAdapter.onPlacePicked, View.OnClickListener {

    private Geocoder geocoder;
    private PlacesClient placesClient;
    private TripPlace destination, startPos;
    private AutoCompleteAdapter adapter;

    private View view;
    private AppCompatEditText adressTv, adressTv2;
    private RecyclerView autoCompleteRv;
    private AppCompatImageView pickLocationBtn;
    private ImageButton back_btn;
    private TextView mainTextView;
    private CardView cardView;

    @SuppressLint("ValidFragment")
    public ProfileFragment(TripPlace startPos, TripPlace destination) {
        this.destination = destination;
        this.startPos = startPos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView();
        initAutoComplete();

        return view;
    }

    public void initView() {
        adressTv = view.findViewById(R.id.adress_tv);
        adressTv2 = view.findViewById(R.id.adress_tv_2);
        cardView = view.findViewById(R.id.top_card_view);
        autoCompleteRv = view.findViewById(R.id.autoCompleteRv);
        pickLocationBtn = view.findViewById(R.id.pickLocationBtn);
        back_btn = view.findViewById(R.id.back_btn);

        cardView.setBackgroundResource(R.drawable.card_view_bg);
        autoCompleteRv.setLayoutManager(new LinearLayoutManager(getContext()));
        back_btn.setOnClickListener(this);
    }

    public void initAutoComplete() {

        geocoder = new Geocoder(getContext());

        if (startPos == null) {
           startPos = new TripPlace();
        }
        if (destination == null) {
          destination = new TripPlace();
        }

        adressTv.setText(startPos.getLocale());
        adressTv2.setText(destination.getLocale());

        pickLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adressTv.getText().length() != 0) {
                    Address dest;
                    try {
                        dest = getLocationFromAddress(getContext(), adressTv.getText().toString());
                    } catch (RuntimeException e) {
                        dest = null;
                    }
                    if (dest != null) {
                        startPos.setCoord(new LatLng(dest.getLatitude(), dest.getLongitude()));
                        startPos.setLocale(dest.getThoroughfare() + " " + dest.getLocality() + ", "
                                + dest.getAdminArea() + ", " + dest.getCountryName());
                    } else {
                        Toast.makeText(getContext(), "Please enter right address", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter destination address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (adressTv2.getText().length() != 0) {
                    Address dest;
                    try {
                        dest = getLocationFromAddress(getContext(), adressTv2.getText().toString());
                    } catch (RuntimeException e) {
                        dest = null;
                    }
                    if (dest != null) {
                        destination.setCoord(new LatLng(dest.getLatitude(), dest.getLongitude()));
                        destination.setLocale(dest.getThoroughfare() + " " + dest.getLocality() + ", "
                                + dest.getAdminArea() + ", " + dest.getCountryName());
                        ((ContentActivity) getActivity()).onDestinationPicked(startPos, destination);
                    } else {
                        Toast.makeText(getContext(), "Please enter right address", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter destination address", Toast.LENGTH_LONG).show();
                }
            }
        });

        adapter = new AutoCompleteAdapter(new ArrayList<>(), ProfileFragment.this);
        autoCompleteRv.setAdapter(adapter);

        //Autocomplete Realisation
        adressTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mainTextView = adressTv;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                placesClient = Places.createClient(getContext());
                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setQuery(charSequence.toString())
                        .build();
                placesClient.findAutocompletePredictions(request).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<FindAutocompletePredictionsResponse> task) {
                        adapter.setData(task.getResult().getAutocompletePredictions());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adressTv2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mainTextView = adressTv2;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                placesClient = Places.createClient(getContext());
                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setQuery(charSequence.toString())
                        .build();
                placesClient.findAutocompletePredictions(request).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<FindAutocompletePredictionsResponse> task) {
                        adapter.setData(task.getResult().getAutocompletePredictions());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public Address getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        Address result_location = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            result_location = address.get(0);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return result_location;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                getActivity().onBackPressed();
        }
    }

    @Override
    public void onPlace(AutocompletePrediction prediction) {
        mainTextView.setText(prediction.getPrimaryText(null));
        destination.setLocale(prediction.getFullText(null).toString());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(prediction.getPlaceId(), placeFields);
        placesClient.fetchPlace(request).addOnCompleteListener(new OnCompleteListener<FetchPlaceResponse>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<FetchPlaceResponse> task) {
                Log.d("tag", task.getResult().getPlace().getName());
                destination.setCoord(task.getResult().getPlace().getLatLng());
                if (mainTextView.equals(adressTv2)) {
                    closeKeyboard();
                    ((ContentActivity) getActivity()).popBackStack();
                    ((ContentActivity) getActivity()).onDestinationPicked(startPos, destination);
                }
                if (mainTextView.equals(adressTv)) {
                    startPos.setCoord(destination.getCoord());
                }
            }
        });
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}