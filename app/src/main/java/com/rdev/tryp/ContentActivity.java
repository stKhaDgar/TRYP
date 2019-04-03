package com.rdev.tryp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.navigation.NavigationView;
import com.rdev.tryp.autocomplete.AdressListFragment;
import com.rdev.tryp.blocks.connect.ConnectFragment;
import com.rdev.tryp.blocks.favourite_drivers.FavouriteDriversFragment;
import com.rdev.tryp.blocks.forme.ProfileFragment;
import com.rdev.tryp.blocks.invite_friends.InviteFriendsFragment;
import com.rdev.tryp.blocks.reward_profile.RewardPointsFragment;
import com.rdev.tryp.blocks.reward_profile.RewardProfileFragment;
import com.rdev.tryp.blocks.reward_profile.RewardWithdrawFragment;
import com.rdev.tryp.blocks.screens.completedRide.CompletedRideFragment;
import com.rdev.tryp.blocks.screens.completedRide.FeedbackDriverFragment;
import com.rdev.tryp.firebaseDatabase.model.Driver;
import com.rdev.tryp.firebaseDatabase.model.Ride;
import com.rdev.tryp.firebaseDatabase.utils.TrypDatabase;
import com.rdev.tryp.intro.IntroActivity;
import com.rdev.tryp.intro.manager.AccountManager;
import com.rdev.tryp.model.RealmUtils;
import com.rdev.tryp.model.TripPlace;
import com.rdev.tryp.model.login_response.Users;
import com.rdev.tryp.payment.AddCardFragment;
import com.rdev.tryp.payment.PaymentFragment;
import com.rdev.tryp.trip.TripFragment;
import com.rdev.tryp.trip.detailFragment.DetailHostFragment;
import com.rdev.tryp.trip.tryp_car.TrypCarHostFragment;
import com.rdev.tryp.blocks.screens.help.HelpFragment;
import com.rdev.tryp.blocks.screens.invite2.Invite2Fragment;
import com.rdev.tryp.blocks.screens.invite3.Invite3Fragment;
import com.rdev.tryp.blocks.screens.legal.LegalFragment;
import com.rdev.tryp.blocks.screens.notifications.NotificationsFragment;
import com.rdev.tryp.blocks.screens.recap.RecapFragment;
import com.rdev.tryp.utils.CurrentLocation;
import com.rdev.tryp.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.realm.Realm;

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */

public class ContentActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    public TrypDatabase database = new TrypDatabase();

    private static final String EXTRA_CONTENT = "content_key";
    public static final String IS_EDIT_CARD = "is_edit_card";

    public static final int TYPE_RECAP = 0;
    public static final int TYPE_LEGAL = 1;
    public static final int TYPE_INVITE1 = 2;
    public static final int TYPE_INVITE2 = 3;
    public static final int TYPE_INVITE3 = 4;
    public static final int TYPE_HELP = 5;
    public static final int TYPE_NOTIFICATIONS = 6;
    public static final int TYPE_HOME = 7;
    public static final int TYPE_TRIP_HISTORY = 8;
    public static final int TYPE_PAYMENT = 9;
    public static final int TYPE_NOTIFICATION = 10;
    public static final int TYPE_REWARDS = 11;
    public static final int TYPE_EMERGENCY_CONTACT = 12;
    public static final int TYPE_PROMOTION = 13;
    public static final int TYPE_INVITE_FRIENDS = 14;
    public static final int TYPE_ABOUT_US = 15;
    public static final int TYPE_LOGOUT = 16;
    public static final int TYPE_MAP = 17;
    public static final int TYPE_FAVORITE = 18;
    public static final int TYPE_REWARD_POINTS = 19;
    public static final int TYPE_PAYMENT_NEW_ENTRY = 20;
    public static final int TYPE_CONNECT = 21;
    public static final int TYPE_REWARDS_WITHDRAW = 22;
    public static final int TYPE_RIDE_COMPLETED = 23;
    public static final int TYPE_WRITE_FEEDBACK = 24;

    private static final int REQUEST_CHECK_SETTINGS = 1001;
    boolean isLocationFound = false;
    public Bundle b = null;

    private static final String TAG = "tag";
    public GoogleMap mMap;
    FragmentManager fm;
    public LatLng pickUpLocation;
    int TYPE_PICK_POSITION = 1;
    int TYPE_VIEWER = 2;
    int type = 2;
    private String currentLocate;
    AdressListFragment listFragment;
    Marker pickAddressMarker;
    public Marker myCurrentLocationMarker = null;
    GroundOverlay currentCar = null;
    private float currentFare = 0;
    public Ride currentRide = null;

    public CurrentLocation currentLocation;
    public String currentAddress = null;

    //route
    public static TripPlace tripFrom;
    public static TripPlace tripTo;
    public static Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Realm.init(ContentActivity.this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        currentLocation = new CurrentLocation(ContentActivity.this, ContentActivity.this);
        currentLocation.init();

        initMenu();
        initMap();

        startFragment(TYPE_WRITE_FEEDBACK);
    }

    public void initMap() {
        Intent intent = getIntent();

        Places.initialize(getApplicationContext(), "AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o");
        fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Fragment fragment;
        String intentTag = intent.getStringExtra("tag");
        switch (intentTag) {
            case "tryp":
                fragment = new TripFragment(new LatLng(25, 26), new LatLng(25, 27));
                break;
            case "car":
                fragment = new TrypCarHostFragment();
                break;
            case "detail":
                fragment = new DetailHostFragment();
                break;
            case "confirm":
                fragment = new ConfirmFragment();
                break;
            case "set":
                fragment = new SetLocationFragment();
                break;
            case "connect":
                fragment = new ConnectFragment(driver);
                break;
            default:
                fragment = new MapNextTrip();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("detail")
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setCompassEnabled(false);
        database.initializeAvailableDrivers(mMap);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            } else {
                checkLocationInSettings();
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            checkLocationInSettings();
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void checkLocationInSettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(ContentActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
        task.addOnSuccessListener(this, locationSettingsResponse -> {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Location location = null;
            if (gps_enabled)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (network_enabled)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            try {
                updateCurrentLocation(location);
            } catch (Exception e) {
                e.printStackTrace();
            }

            locationManager.removeUpdates(mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
        });
    }

    Marker currentPosMarker;

    void updateCurrentLocation(Location location) throws IOException {
        Log.e("LocationUpdate", location.toString());
        LatLng currentPos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
        if (currentPosMarker != null && currentPosMarker.isVisible()) {
            currentPosMarker.remove();
        }

        int height = 270;
        int width = 225;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.current_location_marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap markerBitmap = Bitmap.createScaledBitmap(b, width, height, false);

        if(myCurrentLocationMarker == null){
            myCurrentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentPos)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));

        } else {
            myCurrentLocationMarker.setPosition(currentPos);
        }

        isLocationFound = true;
        Geocoder geocoder = new Geocoder(ContentActivity.this);
        List<Address> addressList = geocoder.getFromLocation(currentPos.latitude, currentPos.longitude, 1);
        currentLocate = addressList.get(0).getThoroughfare() + " " + addressList.get(0).getLocality() + ", "
                + addressList.get(0).getAdminArea() + ", " + addressList.get(0).getCountryName();
        Log.d("tag", addressList.toString());
        pickUpLocation = currentPos;
    }

    void updateCurrentLocation(LatLng currentPos) {
        if(currentPos != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));

            if (currentPosMarker != null && currentPosMarker.isVisible()) {
                currentPosMarker.remove();
            }

            isLocationFound = true;
            pickUpLocation = currentPos;
        }
    }

    final LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(final Location location) {
            Log.d("tag", "onLocation changed");
            try {
                if (!isLocationFound) {
                    updateCurrentLocation(location);
                }
            } catch (NullPointerException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }
        @Override
        public void onProviderEnabled(String s) { }
        @Override
        public void onProviderDisabled(String s) { }
    };

    public void popBackStack() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

    public void clearBackStack() {
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void pickAdress() {
        listFragment = new AdressListFragment();
        fm.beginTransaction().replace(R.id.container, listFragment)
                .addToBackStack("adress").commit();
        type = TYPE_PICK_POSITION;
    }

    Polyline route;

    public void showDirectionPicker(TripPlace destination) {

        if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                ProfileFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(ProfileFragment.class.getName(), 0);
        } else {
            TripPlace currentPlace = new TripPlace();
            currentPlace.setCoord(pickUpLocation);
            currentPlace.setLocale(currentLocate);

            Fragment fragment = new ProfileFragment(currentPlace, destination);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.screenContainer, fragment)
                    .addToBackStack(ProfileFragment.class.getName())
                    .commit();
        }
    }

    public void onDestinationPicked(final TripPlace startPlace, final TripPlace destination, boolean isShowCars) {
        if (pickAddressMarker != null && pickAddressMarker.isVisible()) {
            pickAddressMarker.remove();
        }

        if (route != null && route.isVisible()) {
            route.remove();
        }

        if (startPlace != null) {
            pickUpLocation = startPlace.getCoord();
            updateCurrentLocation(startPlace.getCoord());
        }

        if (pickUpLocation == null) {
            Toast toast = Toast.makeText(this, "Error. We could not determine your current location", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        if(currentCar != null){
            currentCar.setPosition(new LatLng(startPlace.getCoord().latitude, startPlace.getCoord().longitude));
        }

        int height = 270;
        int width = 225;

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.destination_marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap markerBitmap = Bitmap.createScaledBitmap(b, width, height, false);

        pickAddressMarker = mMap.addMarker(new MarkerOptions().position(destination.getCoord())
                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
        type = TYPE_VIEWER;

        Thread thread = new Thread(() -> GoogleDirection.withServerKey("AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
                .from(pickUpLocation)
                .to(destination.getCoord())
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            for (int i = 0; i < direction.getRouteList().get(0).getLegList().size(); i++) {
                                Leg leg = direction.getRouteList().get(0).getLegList().get(i);
                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                if(isShowCars){
                                    currentFare = Integer.parseInt(leg.getDistance().getValue()) / 1000;
                                }
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(ContentActivity.this, directionPositionList, 5, Color.BLUE);
                                route = mMap.addPolyline(polylineOptions);
                            }

                            if(isShowCars){
                                fm.beginTransaction()
                                        .replace(R.id.container, new TripFragment(pickUpLocation, destination.getCoord()))
                                        .addToBackStack("dest_pick")
                                        .commit();
                            }

                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int padding = 200;
                            int width1 = size.x;
                            int height1 = size.y;

                            /*create for loop/manual to add LatLng's to the LatLngBounds.Builder*/
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(destination.getCoord());
                            builder.include(pickUpLocation);

                            /*initialize the padding for map boundary
                            create the bounds from latlngBuilder to set into map camera*/
                            LatLngBounds bounds = builder.build();

                            /*create the camera with bounds and padding to set into map*/
                            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width1, height1 / 2, padding);
                            mMap.animateCamera(cu);

                            tripFrom = startPlace;
                            tripTo = destination;
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                }));

        thread.run();
    }

    public void onDriverRoad(final TripPlace startPlace, final TripPlace destination) {

        if (pickAddressMarker != null && pickAddressMarker.isVisible()) {
            pickAddressMarker.remove();
        }

        if (route != null && route.isVisible()) {
            route.remove();
        }

        if (startPlace != null) {
            pickUpLocation = startPlace.getCoord();
            if(currentCar == null){
                updateCurrentLocation(startPlace.getCoord());
            }
        }

        int height = 270;
        int width = 225;

        BitmapDrawable bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.current_location_marker);
        Bitmap b = bitmapDraw.getBitmap();
        Bitmap markerBitmap = Bitmap.createScaledBitmap(b, width, height, false);

        if(myCurrentLocationMarker == null){
            myCurrentLocationMarker = mMap.addMarker(new MarkerOptions().position(destination.getCoord())
                    .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
        }

        type = TYPE_VIEWER;

        Thread thread = new Thread(() -> GoogleDirection.withServerKey("AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
                .from(pickUpLocation)
                .to(destination.getCoord())
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    boolean cameraIsUpdated = false;

                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            for (int i = 0; i < direction.getRouteList().get(0).getLegList().size(); i++) {
                                Leg leg = direction.getRouteList().get(0).getLegList().get(i);
                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(ContentActivity.this, directionPositionList, 5, Color.BLUE);
                                route = mMap.addPolyline(polylineOptions);
                                route.setZIndex(12);
                            }
                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int padding = 200;
                            int width1 = size.x;
                            int height1 = size.y;

                            /*create for loop/manual to add LatLng's to the LatLngBounds.Builder*/
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(destination.getCoord());
                            builder.include(pickUpLocation);

                            /*initialize the padding for map boundary
                            create the bounds from latlngBuilder to set into map camera*/
                            LatLngBounds bounds = builder.build();

                            /*create the camera with bounds and padding to set into map*/
                            if(!cameraIsUpdated){
                                final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width1, height1 / 2, padding);
                                mMap.animateCamera(cu);
                                cameraIsUpdated = true;
                            }

                            tripFrom = startPlace;
                            tripTo = destination;
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) { }
                }));

        thread.run();
    }

    public void openCarsFragments(List<?> drivers, int currentPos) {

        TrypCarHostFragment fragment = new TrypCarHostFragment();
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("demand")
                .commit();
        fragment.setDrivers(drivers, currentPos);

    }

    public void zoomToCurrentLocation() {

        currentLocation.onStartLocationUpdate(location -> {
            Log.e("GeoLocation", "find current Location = " + location.getLatitude() + " : " + location.getLongitude());
            LatLng currentPos = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 15));

            Geocoder geocoder = new Geocoder(ContentActivity.this, Locale.getDefault());
            String address = null;
            try {
                address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            currentAddress = address;
            ImageView v = findViewById(R.id.currentLocationMarker);

            String url = new RealmUtils(ContentActivity.this, null).getCurrentUser().getImage();
            if(url != null && !url.equals("null") && !url.isEmpty()){
                Picasso.get().load(url).into(v, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap b = Bitmap.createBitmap( getResources().getDimensionPixelOffset(R.dimen.marker_width), getResources().getDimensionPixelOffset(R.dimen.marker_height), Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(b);
                        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        v.draw(c);

                        if(myCurrentLocationMarker != null){
                            myCurrentLocationMarker.remove();
                            myCurrentLocationMarker = null;
                        }

                        myCurrentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentPos)
                                .icon(BitmapDescriptorFactory.fromBitmap(b)));
                    }

                    @Override
                    public void onError(Exception e) {
                        v.setImageResource(R.drawable.default_image);
                        Bitmap b = Bitmap.createBitmap( getResources().getDimensionPixelOffset(R.dimen.marker_width), getResources().getDimensionPixelOffset(R.dimen.marker_height), Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(b);
                        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        v.draw(c);

                        if(myCurrentLocationMarker != null){
                            myCurrentLocationMarker.remove();
                            myCurrentLocationMarker = null;
                        }

                        myCurrentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentPos)
                                .icon(BitmapDescriptorFactory.fromBitmap(b)));
                    }
                });
            } else {
                v.setImageResource(R.drawable.default_image);
                Bitmap b = Bitmap.createBitmap( getResources().getDimensionPixelOffset(R.dimen.marker_width), getResources().getDimensionPixelOffset(R.dimen.marker_height), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                v.draw(c);

                if(myCurrentLocationMarker != null){
                    myCurrentLocationMarker.remove();
                    myCurrentLocationMarker = null;
                }

                myCurrentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentPos)
                        .icon(BitmapDescriptorFactory.fromBitmap(b)));
            }
        });
    }

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    NavigationView.OnNavigationItemSelectedListener listener;

    public void initMenu() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(this);

        Users user = new RealmUtils(ContentActivity.this, null).getCurrentUser();

        String img = user.getImage();
        if(img != null && !img.equals("null")){
            ImageView iv = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
            Picasso.get().load(img).resize(150, 150).into(iv);
        }

        String tempName = user.getFirstName() + " " + user.getLastName();
        TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvName.setText(tempName);

        listener = menuItem -> {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    openMap();
                    break;
//                    case R.id.nav_trip_history:
//                        startFragment(TYPE_TRIP_HISTORY);
//                        break;

//                            case R.id.nav_payment:
//                                //Toast.makeText(getApplicationContext(), "Payment", Toast.LENGTH_SHORT).show();
//                                startFragment(TYPE_PAYMENT);
//                                break;
//                            case R.id.nav_notification:
//                                Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_NOTIFICATION);
//                                break;
                case R.id.nav_rewards:
                    startFragment(TYPE_REWARDS);
                    break;
//                            case R.id.nav_emergency_contact:
//                                Toast.makeText(getApplicationContext(), "Emergency contact", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_EMERGENCY_CONTACT);
//                                break;
//                            case R.id.nav_promotion:
//                                Toast.makeText(getApplicationContext(), "Promotion", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_PROMOTION);
//                                break;
//                            case R.id.nav_invite_friends:
//                                Toast.makeText(getApplicationContext(), "Invite Friends", Toast.LENGTH_SHORT).show();
//                                // startFragment(TYPE_INVITE_FRIENDS);
//                                break;
                case R.id.nav_help:
                    startFragment(TYPE_HELP);
                    break;
                case R.id.nav_favorite:
                    startFragment(TYPE_FAVORITE);
                    break;
                case R.id.nav_payment:
                    startFragment(TYPE_PAYMENT);
                    break;
//                            case R.id.nav_about_us:
//                                Toast.makeText(getApplicationContext(), "About us", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_ABOUT_US);
//                                break;
                case R.id.nav_logout:
                    signOut();
                    break;
                default:
            }
            return true;
        };

        navigationView.setNavigationItemSelectedListener(listener);

        // map menu item checked by default on start
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openMap() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while (count != 1) {
            popBackStack();
            count--;
        }
    }

    public void goHome() {
        navigationView.getMenu().getItem(0).setChecked(true);
        openMap();
        clearMap();
        zoomToCurrentLocation();
    }

    public void goHomeOneTransition(){
        navigationView.getMenu().getItem(0).setChecked(true);
        popBackStack();
        clearMap();
        initMap();
        zoomToCurrentLocation();
    }

    public void updateAvatar(){
        Users user = new RealmUtils(null, null).getCurrentUser();

        String url = user.getImage();
        ImageView iv = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        Picasso.get().load(url).centerCrop().resize(200, 200).into(iv);

        String tempName = user.getFirstName() + " " + user.getLastName();
        TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvName.setText(tempName);

        List<Fragment> list = getSupportFragmentManager().getFragments();

        for(int i=0; i<list.size()-1; i++){
            if(list.get(i) instanceof MapNextTrip){
                ((MapNextTrip) list.get(i)).initUI();
            }
        }

        zoomToCurrentLocation();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private Fragment createFragment() {
        int type = getIntent().getIntExtra(EXTRA_CONTENT, TYPE_RECAP);
        switch (type) {
            case TYPE_RECAP:
                return new RecapFragment();
            case TYPE_LEGAL:
                return new LegalFragment();
            case TYPE_INVITE1:
                return new InviteFriendsFragment();
            case TYPE_INVITE2:
                return new Invite2Fragment();
            case TYPE_INVITE3:
                return new Invite3Fragment();
            case TYPE_HELP:
                return new HelpFragment();
            case TYPE_NOTIFICATIONS:
                return new NotificationsFragment();
            case TYPE_TRIP_HISTORY:
                return new ProfileFragment(null, null);
            case TYPE_PAYMENT:
                Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
            default:
                throw new IllegalStateException("Unknown screen type");
        }
    }

    public void openDetailHost(List<?> drivers, int currentPos) {
        DetailHostFragment fragment = new DetailHostFragment();
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("detail")
                .commit();
        fragment.setDrivers(drivers, currentPos);
    }

    public void startFragment(int type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case TYPE_RECAP:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        RecapFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(RecapFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new RecapFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(RecapFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_LEGAL:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        LegalFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(LegalFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new LegalFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(LegalFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_INVITE1:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        InviteFriendsFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(InviteFriendsFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new InviteFriendsFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(InviteFriendsFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_INVITE2:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        Invite2Fragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(Invite2Fragment.class.getName(), 0);
                } else {
                    Fragment fragment = new Invite2Fragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(Invite2Fragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_INVITE3:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        Invite3Fragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(Invite3Fragment.class.getName(), 0);
                } else {
                    Fragment fragment = new Invite3Fragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(Invite3Fragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_HELP:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        HelpFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(HelpFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new HelpFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(HelpFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_NOTIFICATIONS:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        NotificationsFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(NotificationsFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new NotificationsFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(NotificationsFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_FAVORITE:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        FavouriteDriversFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(FavouriteDriversFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new FavouriteDriversFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(FavouriteDriversFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_REWARDS:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        RewardProfileFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(RewardProfileFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new RewardProfileFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(RewardProfileFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_REWARD_POINTS:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        RewardPointsFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(RewardPointsFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new RewardPointsFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(RewardPointsFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_PAYMENT_NEW_ENTRY:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        AddCardFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(AddCardFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new AddCardFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(AddCardFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_PAYMENT:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        PaymentFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(PaymentFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new PaymentFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(PaymentFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_CONNECT:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        ConnectFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(ConnectFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new ConnectFragment(driver);
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(ConnectFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_REWARDS_WITHDRAW:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        RewardWithdrawFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(RewardWithdrawFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new RewardWithdrawFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(RewardWithdrawFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_RIDE_COMPLETED:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        CompletedRideFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(CompletedRideFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new CompletedRideFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(CompletedRideFragment.class.getName())
                            .commit();
                }
                break;
            case TYPE_WRITE_FEEDBACK:
                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                        FeedbackDriverFragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(FeedbackDriverFragment.class.getName(), 0);
                } else {
                    Fragment fragment = new FeedbackDriverFragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(FeedbackDriverFragment.class.getName())
                            .commit();
                }
                break;
//            case TYPE_TRIP_HISTORY:
//                if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
//                        ProfileFragment.class.getName())) {
//                    getSupportFragmentManager().popBackStackImmediate(ProfileFragment.class.getName(), 0);
//                } else {
//                    Fragment fragment = new ProfileFragment();
//                    transaction.replace(R.id.screenContainer, fragment)
//                            .addToBackStack(ProfileFragment.class.getName())
//                            .commit();
//                }
//                break;
            default:
                throw new IllegalStateException("Unknown screen type");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_icon:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    private void signOut() {
        AccountManager.getInstance().signOut(ContentActivity.this);
        Intent intent = new Intent(ContentActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() == 2){
                clearMap();
            }
            super.onBackPressed();
        }
    }

    public GroundOverlay addGroundOverlay(TripPlace startPlace){
        GroundOverlay go = mMap.addGroundOverlay(new GroundOverlayOptions().position(new LatLng(startPlace.getCoord().latitude, startPlace.getCoord().longitude), 200f).
                image(BitmapDescriptorFactory.fromResource(R.drawable.marker_car)));

        mMap.setOnCameraMoveListener(() -> {
            float zoom = mMap.getCameraPosition().zoom;

            go.setDimensions((float) Math.pow(2.35, (20 - zoom)) + 40);
            database.updateZoomDrivers(zoom);
        });

        return go;
    }

    public float getCurrentFare(){
        return currentFare;
    }

    public void clearMap() {
        mMap.clear();
        updateCurrentLocation(pickUpLocation);
        zoomToCurrentLocation();
    }

    public void showConnectFragment(){
        openMap();
        popBackStack();
        startFragment(ContentActivity.TYPE_CONNECT);
    }

}