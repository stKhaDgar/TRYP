package com.rdev.tryp.blocks.forme.edit_addresses;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.rdev.tryp.model.TripPlace;
import com.rdev.tryp.model.favorite_address.Address;
import com.rdev.tryp.model.favorite_address.FavoriteAddress;
import com.rdev.tryp.model.favorite_address.FavoriteAddressModel;
import com.rdev.tryp.model.favorite_address.FavoriteAddressResponse;
import com.rdev.tryp.network.ApiClient;
import com.rdev.tryp.network.ApiService;
import com.rdev.tryp.utils.PreferenceManager;
import com.rdev.tryp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rdev.tryp.utils.Utils.KEY_HOME;
import static com.rdev.tryp.utils.Utils.KEY_WORK;

public class AddressNetworkService {

    public static void saveAddresses(TripPlace newHome, TripPlace newWork){
        setHomeAddress(newHome);
        setWorkAddress(newWork);
    }

    public static void setHomeAddress(TripPlace newAddress) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        FavoriteAddressModel model = new FavoriteAddressModel();
        TripPlace lastHome = PreferenceManager.getTripPlace(KEY_HOME);

        if(newAddress == null){
            return;
        }

        if (lastHome != null) {
            model.setAddress(lastHome.getLocale());
            model.setLat(String.valueOf(lastHome.getCoord().latitude));
            model.setLng(String.valueOf(lastHome.getCoord().longitude));
            model.setType(Utils.HOME_ADDRESS);
            model.setUserId(18512);//TODO: set by real

            apiService.remove_favorite_address(model).enqueue(new Callback<FavoriteAddressResponse>() {
                @Override
                public void onResponse(Call<FavoriteAddressResponse> call, Response<FavoriteAddressResponse> response) {
                    FavoriteAddressResponse body = response.body();
                    if (body != null) {
                        Log.i("remove home address", model.getAddress());
                    }
                }

                @Override
                public void onFailure(Call<FavoriteAddressResponse> call, Throwable t) {
                    Log.i("remove home address", "failure");
                }
            });
        }

        if (lastHome == null || !newAddress.getLocale().equals(lastHome.getLocale())) {
            model.setAddress(newAddress.getLocale());
            model.setLat(String.valueOf(newAddress.getCoord().latitude));
            model.setLng(String.valueOf(newAddress.getCoord().longitude));
            model.setType(Utils.HOME_ADDRESS);
            model.setUserId(18512);//TODO: set by real

            apiService.add_favorite_address(model).enqueue(new Callback<FavoriteAddressResponse>() {
                @Override
                public void onResponse(Call<FavoriteAddressResponse> call, Response<FavoriteAddressResponse> response) {
                    FavoriteAddressResponse body = response.body();
                    if (body != null) {
                        Log.i("add home address", model.getAddress());
                    }
                }

                @Override
                public void onFailure(Call<FavoriteAddressResponse> call, Throwable t) {
                    Log.i("add home address", "failure");
                }
            });
        }

        PreferenceManager.setTripPlace(KEY_HOME, newAddress);
    }

    public static void setWorkAddress(TripPlace newAddress) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        FavoriteAddressModel model = new FavoriteAddressModel();
        TripPlace lastWork = PreferenceManager.getTripPlace(KEY_WORK);

        if(newAddress == null){
            return;
        }

        if (lastWork != null) {
            model.setAddress(lastWork.getLocale());
            model.setLat(String.valueOf(lastWork.getCoord().latitude));
            model.setLng(String.valueOf(lastWork.getCoord().longitude));
            model.setType(Utils.WORK_ADDRESS);
            model.setUserId(18512);//TODO: set by real

            apiService.remove_favorite_address(model).enqueue(new Callback<FavoriteAddressResponse>() {
                @Override
                public void onResponse(Call<FavoriteAddressResponse> call, Response<FavoriteAddressResponse> response) {
                    FavoriteAddressResponse body = response.body();
                    if (body != null) {
                        Log.i("remove work address", model.getAddress());
                    }
                }

                @Override
                public void onFailure(Call<FavoriteAddressResponse> call, Throwable t) {
                    Log.i("remove work address", "failure");
                }
            });
        }

        if(newAddress == null || lastWork == null){
            return;
        }

        if (lastWork == null || !newAddress.getLocale().equals(lastWork.getLocale())) {
            model.setAddress(newAddress.getLocale());
            model.setLat(String.valueOf(newAddress.getCoord().latitude));
            model.setLng(String.valueOf(newAddress.getCoord().longitude));
            model.setType(Utils.WORK_ADDRESS);
            model.setUserId(18512);//TODO: set by real

            apiService.add_favorite_address(model).enqueue(new Callback<FavoriteAddressResponse>() {
                @Override
                public void onResponse(Call<FavoriteAddressResponse> call, Response<FavoriteAddressResponse> response) {
                    FavoriteAddressResponse body = response.body();
                    if (body != null) {
                        Log.i("add work address", model.getAddress());
                    }
                }

                @Override
                public void onFailure(Call<FavoriteAddressResponse> call, Throwable t) {
                    Log.i("add work address", "failure");
                }
            });
        }
        PreferenceManager.setTripPlace(KEY_WORK, newAddress);
    }

    public static void initFavoriteAddresses(){
        setUpHomeAddress();
        setUpWorkAddress();
    }

    public static void setUpWorkAddress(){
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        apiService.get_favourite_address("18512", Utils.WORK_ADDRESS).enqueue(new Callback<FavoriteAddress>() {
            @Override
            public void onResponse(Call<FavoriteAddress> call, Response<FavoriteAddress> response) {
                if(response.body().getData().getFavoriteAddresses() == null){
                    return;
                }
                Address address = response.body().getData().getFavoriteAddresses().getAddress();
                if (address == null) {
                    return;
                }
                TripPlace tripPlace = new TripPlace();
                tripPlace.setLocale(address.getAddress());
                tripPlace.setCoord(new LatLng(address.getLat(), address.getLng()));
                PreferenceManager.setTripPlace(KEY_WORK, tripPlace);
                Log.i("get work address", address.getAddress());
            }

            @Override
            public void onFailure(Call<FavoriteAddress> call, Throwable t) {
                Log.i("get work address", "failure");
            }
        });
    }

    public static void setUpHomeAddress(){
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        apiService.get_favourite_address("18512", Utils.HOME_ADDRESS).enqueue(new Callback<FavoriteAddress>() {
            @Override
            public void onResponse(Call<FavoriteAddress> call, Response<FavoriteAddress> response) {
                if(response.body().getData().getFavoriteAddresses() == null){
                    return;
                }
                Address address = response.body().getData().getFavoriteAddresses().getAddress();
                if (address == null) {
                    return;
                }
                TripPlace tripPlace = new TripPlace();
                tripPlace.setLocale(address.getAddress());
                tripPlace.setCoord(new LatLng(address.getLat(), address.getLng()));
                PreferenceManager.setTripPlace(KEY_HOME, tripPlace);
                Log.i("get home address", address.getAddress());
            }

            @Override
            public void onFailure(Call<FavoriteAddress> call, Throwable t) {
                Log.i("get home address", "failure");
            }
        });
    }
}
