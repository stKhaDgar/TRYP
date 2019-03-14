package com.rdev.tryp.blocks.forme.edit_addresses;

import android.annotation.SuppressLint;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.rdev.tryp.R;
import com.rdev.tryp.autocomplete.AutoCompleteAdapter;
import com.rdev.tryp.model.TripPlace;
import com.rdev.tryp.utils.PreferenceManager;
import com.rdev.tryp.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import afu.org.checkerframework.checker.nullness.qual.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.rdev.tryp.utils.Utils.KEY_HOME;
import static com.rdev.tryp.utils.Utils.KEY_WORK;
import static com.rdev.tryp.utils.Utils.closeKeyboard;

@SuppressLint("ValidFragment")
public class EditAddressesFragment extends Fragment implements AutoCompleteAdapter.onPlacePicked, View.OnClickListener {

    private View view;
    private TripPlace home, work;
    private AppCompatEditText homeEt, workEt, mainEt;
    private RecyclerView completeRv;
    private CardView confirmEditBtn;
    private Editor.IView addressView;
    private AutoCompleteAdapter adapter;
    private Geocoder geocoder;
    private PlacesClient placesClient;


    @SuppressLint("ValidFragment")
    public EditAddressesFragment(Editor.IView addressView) {
        this.addressView = addressView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addresses_edit, container, false);

        initView();
        initAutoComplete();

        return view;
    }

    private void initView() {
        homeEt = view.findViewById(R.id.home_edit_text);
        workEt = view.findViewById(R.id.work_edit_text);
        completeRv = view.findViewById(R.id.complete_recycler_view);
        confirmEditBtn = view.findViewById(R.id.confirm_edit_btn);
        confirmEditBtn.setOnClickListener(this);

        completeRv.setLayoutManager(new LinearLayoutManager(getContext()));

        homeEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adapter.setData(new ArrayList<>());
                mainEt = homeEt;
            }
        });
        workEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adapter.setData(new ArrayList<>());
                mainEt = workEt;
            }
        });
    }

    public void initAutoComplete() {
        geocoder = new Geocoder(getContext());
        home = PreferenceManager.getTripPlace(KEY_HOME);
        work = PreferenceManager.getTripPlace(KEY_WORK);

        if(home == null){
            home = new TripPlace();
        }

        if(work == null){
            work = new TripPlace();
        }

        homeEt.setText(home.getLocale());
        workEt.setText(work.getLocale());

        adapter = new AutoCompleteAdapter(new ArrayList<>(), this);
        completeRv.setAdapter(adapter);

        //Autocomplete Realisation
        homeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mainEt = homeEt;
                adapter.setData(new ArrayList<>());
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

        workEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mainEt = workEt;
                adapter.setData(new ArrayList<>());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_edit_btn:
                AddressNetworkService.saveAddresses(home, work);
                addressView.showAddresses();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
        }
    }

    @Override
    public void onPlace(AutocompletePrediction prediction) {
        mainEt.setText(prediction.getPrimaryText(null));
        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(prediction.getPlaceId(), placeFields);
        placesClient.fetchPlace(request).addOnCompleteListener(new OnCompleteListener<FetchPlaceResponse>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<FetchPlaceResponse> task) {
                if (mainEt.equals(workEt)) {
                    work.setCoord(task.getResult().getPlace().getLatLng());
                    work.setLocale(prediction.getFullText(null).toString());
                    Utils.closeKeyboard(getContext());
                    adapter.setData(new ArrayList<>());
                }
                if (mainEt.equals(homeEt)) {
                    home.setCoord(task.getResult().getPlace().getLatLng());
                    home.setLocale(prediction.getFullText(null).toString());
                    Utils.closeKeyboard(getContext());
                    adapter.setData(new ArrayList<>());
                }
            }
        });
    }
}
