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
import com.rdev.tryp.detailFragment.DetailHostFragment;
import com.rdev.tryp.intro.IntroActivity;
import com.rdev.tryp.manager.AccountManager;
import com.rdev.tryp.model.DriversItem;
import com.rdev.tryp.model.TripPlace;
import com.rdev.tryp.trip.TripFragment;
import com.rdev.tryp.tryp_car.TrypCarHostFragment;
import com.rdev.tryp.forme.ProfileFragment;
import com.rdev.tryp.ui_only.screens.help.HelpFragment;
import com.rdev.tryp.ui_only.screens.invite1.Invite1Fragment;
import com.rdev.tryp.ui_only.screens.invite2.Invite2Fragment;
import com.rdev.tryp.ui_only.screens.invite3.Invite3Fragment;
import com.rdev.tryp.ui_only.screens.legal.LegalFragment;
import com.rdev.tryp.ui_only.screens.notifications.NotificationsFragment;
import com.rdev.tryp.ui_only.screens.recap.RecapFragment;
import com.rdev.tryp.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */
public class ContentActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private static final int REQUEST_CHECK_SETTINGS = 1001;
    boolean isLocationFound = false;

    private static final String TAG = "tag";
    private GoogleMap mMap;
    FragmentManager fm;
    public LatLng pickUpLocation;
    int TYPE_PICK_POSITION = 1;
    int TYPE_VIEWER = 2;
    int type = 2;
    private String currentLocate;
    AdressListFragment listFragment;
    Marker pickAdressMarker;
    Marker pickStartMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initMenu();
        initMap();
    }

    public void initMap() {
        Intent intent = getIntent();

        Places.initialize(getApplicationContext(), "AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o");
        fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Fragment fragment;
        String intentTag = intent.getStringExtra("tag");
        if (intentTag.equals("tryp")) {
            fragment = new TripFragment(new LatLng(25, 26), new LatLng(25, 27));
        } else if (intentTag.equals("detail")) {
            fragment = new DetailHostFragment();
        } else if (intentTag.equals("car")) {
            fragment = new TrypCarHostFragment();
        } else if (intentTag.equals("confirm")) {
            fragment = new ConfirmFragment();
        } else if (intentTag.equals("set")) {
            fragment = new SetLocationFragment();
        } else if (intentTag.equals("connect")) {
            fragment = new ConnectFragment();
        } else {
            fragment = new MapNextTrip();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("detail")
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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


        currentPosMarker = mMap.addMarker(new MarkerOptions().position(currentPos)
                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
        isLocationFound = true;
        Geocoder geocoder = new Geocoder(ContentActivity.this);
        List<Address> addressList = geocoder.getFromLocation(currentPos.latitude, currentPos.longitude, 1);
        currentLocate = addressList.get(0).getAddressLine(0);
        Log.d("tag", addressList.toString());
        pickUpLocation = currentPos;
    }

    void updateCurrentLocation(LatLng currentPos) throws IOException {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
        if (currentPosMarker != null && currentPosMarker.isVisible()) {
            currentPosMarker.remove();
        }

        int height = 270;
        int width = 225;

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.current_location_marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap markerBitmap = Bitmap.createScaledBitmap(b, width, height, false);


        currentPosMarker = mMap.addMarker(new MarkerOptions().position(currentPos)
                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
        isLocationFound = true;
        pickUpLocation = currentPos;
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
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void popBackStack() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            finish();
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

    public void onDestinationPicked(final TripPlace startPlace, final TripPlace destination) {
        if (pickAdressMarker != null && pickAdressMarker.isVisible()) {
            pickAdressMarker.remove();
        }

        if (route != null && route.isVisible()) {
            route.remove();
        }

        if (startPlace != null) {
            pickUpLocation = startPlace.getCoord();
            try {
                updateCurrentLocation(startPlace.getCoord());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (pickUpLocation == null) {
            Toast toast = Toast.makeText(this, "Error. We could not determine your current location", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        int height = 270;
        int width = 225;

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.destination_marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap markerBitmap = Bitmap.createScaledBitmap(b, width, height, false);

        pickAdressMarker = mMap.addMarker(new MarkerOptions().position(destination.getCoord())
                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
        type = TYPE_VIEWER;

        fm.beginTransaction()
                .replace(R.id.container, new TripFragment(pickUpLocation, destination.getCoord()))
                .addToBackStack("dest_pick")
                .commit();

        GoogleDirection.withServerKey("AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
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
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(ContentActivity.this, directionPositionList, 5, Color.BLUE);
                                route = mMap.addPolyline(polylineOptions);
                            }
                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int padding = 200;
                            int width = size.x;
                            int height = size.y;

                            /**create for loop/manual to add LatLng's to the LatLngBounds.Builder*/
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(destination.getCoord());
                            builder.include(pickUpLocation);

                            /**initialize the padding for map boundary*/
                            /**create the bounds from latlngBuilder to set into map camera*/
                            LatLngBounds bounds = builder.build();
                            /**create the camera with bounds and padding to set into map*/
                            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height / 2, padding);
                            mMap.animateCamera(cu);
                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });

    }

    public void openCarsFragments(List<?> drivers, int currentPos) {
        TrypCarHostFragment fragment = new TrypCarHostFragment();
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("demand")
                .commit();
        fragment.setDrivers(drivers, currentPos);
    }

    public void openDetailHost(List<?> drivers, int currentPos) {
        DetailHostFragment fragment = new DetailHostFragment();
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("detail")
                .commit();
        fragment.setDrivers(drivers, currentPos);
    }

    public void moveCameraTo(LatLng position) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
    }

    public void setMarkerTo(int resId, LatLng markerPos) {
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(resId))
                .position(markerPos));
    }

    public void zoomToCurrentLocation() {
        if (pickUpLocation == null) {
            checkLocationInSettings();
        } else {
            LatLng currentPos = new LatLng(pickUpLocation.latitude, pickUpLocation.longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
        }
    }

    private static final String EXTRA_CONTENT = "content_key";

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

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    NavigationView.OnNavigationItemSelectedListener listener;

    public static Intent createIntent(Context context, int content) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(EXTRA_CONTENT, content);
        return intent;
    }

    private void requestPermission() {
        int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    public void initMenu() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(this);

        listener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();


                switch (menuItem.getItemId()) {
//                            case R.id.nav_home:
//                                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_HOME);
//                                break;
                    case R.id.nav_map:
                        int count = getSupportFragmentManager().getBackStackEntryCount();
                        while (count != 1) {
                            onBackPressed();
                            count--;
                        }
                        break;
//                    case R.id.nav_trip_history:
//                        startFragment(TYPE_TRIP_HISTORY);
//                        break;
//                            case R.id.nav_payment:
//                                Toast.makeText(getApplicationContext(), "Payment", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_PAYMENT);
//                                break;
//                            case R.id.nav_notification:
//                                Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_NOTIFICATION);
//                                break;
//                            case R.id.nav_rewards:
//                                Toast.makeText(getApplicationContext(), "Rewards", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_REWARDS);
//                                break;
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
//                            case R.id.nav_about_us:
//                                Toast.makeText(getApplicationContext(), "About us", Toast.LENGTH_SHORT).show();
//                                //startFragment(TYPE_ABOUT_US);
//                                break;
                            case R.id.nav_logout:
                                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                                signOut();
                                break;
                    default:
                }
                return true;
            }
        };

        navigationView.setNavigationItemSelectedListener(listener);
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
                return new Invite1Fragment();
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

    private void startFragment(int type) {
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
                        Invite1Fragment.class.getName())) {
                    getSupportFragmentManager().popBackStackImmediate(Invite1Fragment.class.getName(), 0);
                } else {
                    Fragment fragment = new Invite1Fragment();
                    transaction.replace(R.id.screenContainer, fragment)
                            .addToBackStack(Invite1Fragment.class.getName())
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
        if (v.getId() == R.id.menu_icon) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void signOut(){
        AccountManager.getInstance().signOut();
        Intent intent = new Intent(ContentActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }
}
