package com.rdev.tryp.trip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.blocks.connect.ConnectFragment;
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase;
import com.rdev.tryp.firebaseDatabase.DriverApproveListener;
import com.rdev.tryp.firebaseDatabase.model.AvailableDriver;
import com.rdev.tryp.firebaseDatabase.model.Driver;
import com.rdev.tryp.firebaseDatabase.model.Location;
import com.rdev.tryp.firebaseDatabase.model.Ride;
import com.rdev.tryp.firebaseDatabase.utils.TrypDatabase;
import com.rdev.tryp.intro.manager.AccountManager;
import com.rdev.tryp.model.DriversItem;
import com.rdev.tryp.model.RealmUtils;
import com.rdev.tryp.model.TripPlace;
import com.rdev.tryp.model.ride_responce.RequestRideBody;
import com.rdev.tryp.model.ride_responce.RideRequest;
import com.rdev.tryp.model.ride_responce.RideResponse;
import com.rdev.tryp.network.ApiClient;
import com.rdev.tryp.network.ApiService;
import com.rdev.tryp.network.NetworkService;
import com.rdev.tryp.utils.BearingInterpolator;
import com.rdev.tryp.utils.CarAnimation;
import com.rdev.tryp.utils.LatLngInterpolator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rdev.tryp.trip.tryp_car.TrypCarFragment.TYPE_TRYP;
import static com.rdev.tryp.trip.tryp_car.TrypCarFragment.TYPE_TRYP_ASSIST;
import static com.rdev.tryp.trip.tryp_car.TrypCarFragment.TYPE_TRYP_EXTRA;
import static com.rdev.tryp.trip.tryp_car.TrypCarFragment.TYPE_TRYP_PRIME;

@SuppressLint("ValidFragment")
public class TripFragment extends Fragment implements View.OnClickListener {
    private RecyclerView tripRv;
    private ApiService service;
    private TripAdapter tripAdapter;
    private CardView onDemandCard;
    private CardView favouriteCard;
    private TextView favourite_tv;
    private TextView on_demand_tv;

    private LatLng currentPosition;
    private LatLng destinationPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiClient.getInstance().create(ApiService.class);
    }

    @SuppressLint("ValidFragment")
    public TripFragment(LatLng currentPosition, LatLng destinationPosition) {
        this.currentPosition = currentPosition;
        this.destinationPosition = destinationPosition;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trip_fragment, container, false);
        tripRv = v.findViewById(R.id.trip_recycler_view);
        tripRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        onDemandCard = v.findViewById(R.id.on_demand_card);
        onDemandCard.setOnClickListener(this);
        favouriteCard = v.findViewById(R.id.favourite_card);
        favouriteCard.setOnClickListener(this);
        favourite_tv = v.findViewById(R.id.favourite_tv);
        on_demand_tv = v.findViewById(R.id.on_demand_tv);
        ImageView backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        getNearDrivers();
        return v;
    }

    private void getFavouriteDrivers() {

        // set UI
        favourite_tv.setTextColor(Color.WHITE);
        on_demand_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.unselect_tv_color));
        onDemandCard.setCardBackgroundColor(Color.WHITE);
        favouriteCard.setCardBackgroundColor(Color.BLUE);

        TripAdapter.OnItemClickListener listener = item -> ((ContentActivity) getActivity()).openDetailHost(tripAdapter.drivers, tripAdapter.drivers.indexOf(item));

        TrypDatabase database = ((ContentActivity) Objects.requireNonNull(getActivity())).database;

        database.setFavoritesDrivers(drivers -> {
            if(getActivity() != null){
                tripAdapter = new TripAdapter(drivers, TripAdapter.TYPE_CAR, listener, getContext(), ((ContentActivity) getActivity()).getCurrentFare());
            }
            tripRv.setAdapter(tripAdapter);
            tripAdapter.notifyDataSetChanged();
        });
    }

    private void getNearDrivers() {

        // set UI
        onDemandCard.setCardBackgroundColor(Color.BLUE);
        favouriteCard.setCardBackgroundColor(Color.WHITE);
        on_demand_tv.setTextColor(Color.WHITE);
        favourite_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.unselect_tv_color));

        TripAdapter.OnItemClickListener listener = item -> ((ContentActivity) getActivity()).openCarsFragments(tripAdapter.drivers, tripAdapter.drivers.indexOf(item));

        TrypDatabase database = ((ContentActivity) Objects.requireNonNull(getActivity())).database;

        database.setAvailableDrivers(drivers -> {
            if(getActivity() != null){
                tripAdapter = new TripAdapter(drivers, TripAdapter.TYPE_CAR, listener, getContext(), ((ContentActivity) getActivity()).getCurrentFare());
            }
            tripRv.setAdapter(tripAdapter);
            tripAdapter.notifyDataSetChanged();
        });
    }

    private List<?> filterDrivers(List<DriversItem> drivers) {
        List<DriversItem> newDrivers = new ArrayList<>();
        for (DriversItem d : drivers) {
            if (d.getCategory().equals(TYPE_TRYP)) {
                newDrivers.add(d);
                break;
            }
        }
        for (DriversItem d : drivers) {
            if (d.getCategory().equals(TYPE_TRYP_ASSIST)) {
                newDrivers.add(d);
                break;
            }
        }
        for (DriversItem d : drivers) {
            if (d.getCategory().equals(TYPE_TRYP_EXTRA)) {
                newDrivers.add(d);
                break;
            }
        }
        for (DriversItem d : drivers) {
            if (d.getCategory().equals(TYPE_TRYP_PRIME)) {
                newDrivers.add(d);
                break;
            }
        }

        return newDrivers;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.on_demand_card:
                getNearDrivers();
                break;
            case R.id.favourite_card:
                getFavouriteDrivers();
                break;
            case R.id.back_btn:
                ((ContentActivity) getActivity()).clearMap();
                getActivity().onBackPressed();
        }
    }

    public static void orderTrip(Activity activity, Driver driversItem, TripPlace start, TripPlace end) {
        Geocoder geocoder = new Geocoder(activity);
        try {
            List<Address> fromAddress = geocoder.getFromLocation(start.getCoord().latitude, start.getCoord().longitude, 1);
            List<Address> toAddress = geocoder.getFromLocation(end.getCoord().latitude, end.getCoord().longitude, 1);
            ContentActivity.driver = driversItem;

            RequestRideBody requestRideBody = new RequestRideBody(
                    fromAddress.get(0),
                    toAddress.get(0),
                    false,
                    driversItem,
                    AccountManager.getInstance().getUserId());

            Ride ride = new Ride(null,
                    new RealmUtils(activity, null).getCurrentUser().getUserId().toString(),
                    new Location(end.getCoord().latitude, end.getCoord().longitude),
                    new Location(start.getCoord().latitude, start.getCoord().longitude),
                    null,
                    fromAddress.get(0).getAddressLine(0),
                    toAddress.get(0).getAddressLine(0),
                    ((ContentActivity) activity).getCurrentFare());

            NetworkService.getApiService().ride_request(requestRideBody).enqueue(new Callback<RideResponse>() {
                @Override
                public void onResponse(Call<RideResponse> call, final Response<RideResponse> response) {
                    RideResponse body = response.body();
                    if (body == null || body.getData() == null) {
                        showAlertDialod("Ride request Failed", "Error at trip order. Please try again", activity);
                    } else {
                        final RideRequest rideRequest = body.getData().getRideRequest();
                        showAlertDialod("Ride request Successful", "Your ride request succesffully send", activity);
                        new Handler().postDelayed(() -> {
                            ride.setId(rideRequest.getRequestId());
                            ((ContentActivity)activity).database.startRide(ride, driversItem.getDriverId(), new DriverApproveListener() {
                                boolean connectIsShown = false;
                                int status = 0;
                                Pair<GroundOverlay, AvailableDriver> currentCar = null;

                                @Override
                                public void isApproved(Ride ride) {
                                    if(status != 0){
                                        return;
                                    }

                                    if(currentCar == null){
                                        currentCar = new Pair<>(((ContentActivity) activity).addGroundOverlay(new TripPlace(Locale.getDefault().getDisplayCountry(), new LatLng(ride.getDriver().getLocation().getLat(), ride.getDriver().getLocation().getLng()))),
                                                ride.getDriver());
                                        currentCar.first.setZIndex(15);

                                        LatLng startPos = new LatLng(ride.getDriver().getLocation().getLat(), ride.getDriver().getLocation().getLng());
                                        LatLng endPos = new LatLng(ride.getPickUpLocation().getLat(), ride.getPickUpLocation().getLng());

                                        ((ContentActivity)activity).onDriverRoad(
                                                new TripPlace(Locale.getDefault().getDisplayCountry(), startPos),
                                                new TripPlace(Locale.getDefault().getDisplayCountry(), endPos));

                                    } else {
                                        CarAnimation.animateMarkerToGB(currentCar.first, ride.getDriver().getLocation(), new LatLngInterpolator.Spherical(), new BearingInterpolator.Degree());
                                    }

                                    if(!connectIsShown){
                                        ((ContentActivity)activity).showConnectFragment();
                                        connectIsShown = true;
                                    }

                                    Log.e(ConstantsFirebase.TAG, ride.getDriver().getLocation().getLat() + " : " + ride.getDriver().getLocation().getLng());
                                }

                                @Override
                                public void statusChanged(int currentStatus, Ride ride) {
                                    if(currentStatus == ConstantsFirebase.STATUS_ROAD_PREPARED && status == 0){
                                        status = ConstantsFirebase.STATUS_ROAD_PREPARED;

                                        showAlertDialod("Driver waiting", "Please get in the car", activity);

                                        ((ContentActivity) activity).currentRide = ride;
                                    } else if(currentStatus == ConstantsFirebase.STATUS_ROAD_STARTED && status == 150 ){
                                        status = ConstantsFirebase.STATUS_ROAD_STARTED;
                                        ((ContentActivity) activity).popBackStack();

                                        LatLng startPos = new LatLng(ride.getDriver().getLocation().getLat(), ride.getDriver().getLocation().getLng());
                                        LatLng endPos = new LatLng(ride.getDestinationLocation().getLat(), ride.getDestinationLocation().getLng());

                                        ((ContentActivity) activity).onDestinationPicked(
                                                new TripPlace(Locale.getDefault().getDisplayCountry(), startPos),
                                                new TripPlace(Locale.getDefault().getDisplayCountry(), endPos),
                                                false);

                                        ((ContentActivity) activity).myCurrentLocationMarker.setVisible(false);

                                        showAlertDialod("Trip started!", "The driver started the trip", activity);

                                    } else if (currentStatus == ConstantsFirebase.STATUS_ROAD_FINISHED && status == 100){
                                        status = ConstantsFirebase.STATUS_ROAD_FINISHED;
                                        ((ContentActivity) activity).myCurrentLocationMarker.setVisible(true);
                                        ((ContentActivity) activity).zoomToCurrentLocation();
                                        currentCar = null;

                                        ((ContentActivity) activity).startFragment(ContentActivity.TYPE_RIDE_COMPLETED);
                                    }

                                    if(currentCar != null) {
                                        CarAnimation.animateMarkerToGB(currentCar.first, ride.getDriver().getLocation(), new LatLngInterpolator.Spherical(), new BearingInterpolator.Degree());
                                        ((ContentActivity) activity).mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ride.getDriver().getLocation().getLat(), ride.getDriver().getLocation().getLng()), ((ContentActivity) activity).mMap.getCameraPosition().zoom));
                                    }
                                }

                                @Override
                                public void timeUpdated(@NotNull String time) {
                                    List<Fragment> list = ((ContentActivity) activity).getSupportFragmentManager().getFragments();

                                    for(int i=0; i<list.size(); i++){
                                        if(list.get(i) instanceof ConnectFragment){
                                            ((ConnectFragment) list.get(i)).setTime(time);
                                        }
                                    }
                                }

                                @Override
                                public void isDeclined() {
                                    ((ContentActivity)activity).popBackStack();
                                    ((ContentActivity)activity).clearMap();
                                    ((ContentActivity)activity).initMap();
                                }
                            });

                        }, 3000);
                    }
                }

                @Override
                public void onFailure(Call<RideResponse> call, Throwable t) {

                }
            });

        } catch (Exception ex) {
            showAlertDialod("Ride request failed", "Error at trip order", activity);
            ex.printStackTrace();
        }
    }

//    private static void updateStatus(Activity activity, final String requestId) {
//        NetworkService.getApiService().ride_request_status(AccountManager.getInstance().getUserId(), requestId)
//                .enqueue(new Callback<StatusResponse>() {
//            @Override
//            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                if (response.body().getData().getRide().getVoucherNo() != null) {
//                    showAlertDialod("Booking successful", "Your booking has been confirmed.\n" +
//                            "Driver will pickup you in 5 minutes.", activity);
//                    ((ContentActivity)activity).showConnectFragment();
//                } else {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            updateStatus(activity, requestId);
//                        }
//                    }, 3000);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StatusResponse> call, Throwable t) {
//                Log.d("tag", "error");
//            }
//        });
//
//    }

    private static AlertDialog dialog;

    private static void showAlertDialod(String title, String message, Context context) {
        TextView dialog_title_tv;
        ImageButton back_btn;
        TextView dialog_msg_tv;
        View v = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);
        dialog_msg_tv = v.findViewById(R.id.dialog_message);
        back_btn = v.findViewById(R.id.dialog_back_btn);


        dialog = new AlertDialog.Builder(context)
                .setView(v).create();

        back_btn.setOnClickListener(view -> dialog.dismiss());
        if (dialog != null && !title.equals("The trip is over")) {
            dialog.dismiss();
        } else if (title.equals("The trip is over")){
            dialog.dismiss();
            ((ContentActivity) context).clearMap();
            ((ContentActivity) context).initMap();
            ((ContentActivity) context).zoomToCurrentLocation();
        }
        dialog_title_tv = v.findViewById(R.id.dialog_title);
        dialog = new AlertDialog.Builder(context)
                .setView(v).create();
        dialog.show();
        dialog_title_tv.setText(title);
        dialog_msg_tv.setText(message);
    }
}