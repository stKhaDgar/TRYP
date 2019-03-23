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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.firebaseDatabase.DriverApproveListener;
import com.rdev.tryp.firebaseDatabase.model.Driver;
import com.rdev.tryp.firebaseDatabase.model.Location;
import com.rdev.tryp.firebaseDatabase.model.Ride;
import com.rdev.tryp.firebaseDatabase.utils.TrypDatabase;
import com.rdev.tryp.intro.manager.AccountManager;
import com.rdev.tryp.model.DriversItem;
import com.rdev.tryp.model.TripPlace;
import com.rdev.tryp.model.favourite_driver.FavouriteDriver;
import com.rdev.tryp.model.ride_responce.RequestRideBody;
import com.rdev.tryp.model.ride_responce.RideRequest;
import com.rdev.tryp.model.ride_responce.RideResponse;
import com.rdev.tryp.model.status_response.StatusResponse;
import com.rdev.tryp.network.ApiClient;
import com.rdev.tryp.network.ApiService;
import com.rdev.tryp.network.NetworkService;

import java.util.ArrayList;
import java.util.List;
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
    RecyclerView tripRv;
    ApiService service;
    TripAdapter tripAdapter;
    CardView onDemandCard;
    CardView favouriteCard;
    TextView favourite_tv;
    TextView on_demand_tv;

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

        service.get_favourite_drivers(AccountManager.getInstance().getUserId()).enqueue(new Callback<FavouriteDriver>() {

            @Override
            public void onResponse(Call<FavouriteDriver> call, Response<FavouriteDriver> response) {
                //                tripAdapter = new TripAdapter(drivers, TripAdapter.TYPE_DRIVER, listener, getContext());
//                tripRv.setAdapter(tripAdapter);
            }

            @Override
            public void onFailure(Call<FavouriteDriver> call, Throwable t) {

            }
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
            tripAdapter = new TripAdapter(drivers, TripAdapter.TYPE_CAR, listener, getContext());
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
                    new Location(end.getCoord().latitude, end.getCoord().longitude),
                    new Location(start.getCoord().latitude, start.getCoord().longitude),
                    null,
                    fromAddress.get(0).getAddressLine(0),
                    toAddress.get(0).getAddressLine(0));

            NetworkService.getApiService().ride_request(requestRideBody).enqueue(new Callback<RideResponse>() {
                @Override
                public void onResponse(Call<RideResponse> call, final Response<RideResponse> response) {
                    RideResponse body = response.body();
                    if (body == null || body.getData() == null) {
                        showAlertDialod("Ride request Failed", "Error at trip order. Please try again", activity);
                    } else {
                        final RideRequest rideRequest = body.getData().getRideRequest();
                        Toast.makeText(activity, rideRequest.getRequestId(), Toast.LENGTH_LONG).show();
                        showAlertDialod("Ride request Successful", "Your ride request succesffully send", activity);
                        new Handler().postDelayed(() -> {
                            ride.setId(rideRequest.getRequestId());
                            new TrypDatabase().startRide(ride, driversItem.getDriverId(), new DriverApproveListener() {
                                @Override
                                public void isApproved() {
                                    updateStatus(activity, rideRequest.getRequestId());
                                }

                                @Override
                                public void isDeclined() {
                                    ((ContentActivity)activity ).popBackStack();
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
        }
    }

    private static void updateStatus(Activity activity, final String requestId) {
        NetworkService.getApiService().ride_request_status(AccountManager.getInstance().getUserId(), requestId)
                .enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.body().getData().getRide().getVoucherNo() != null) {
                    showAlertDialod("Booking successful", "Your booking has been confirmed.\n" +
                            "Driver will pickup you in 5 minutes.", activity);
                    ((ContentActivity)activity).showConnectFragment();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateStatus(activity, requestId);
                        }
                    }, 3000);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.d("tag", "error");
            }
        });

    }

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
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog_title_tv = v.findViewById(R.id.dialog_title);
        dialog = new AlertDialog.Builder(context)
                .setView(v).create();
        dialog.show();
        dialog_title_tv.setText(title);
        dialog_msg_tv.setText(message);
    }
}