package com.rdev.tryp.network;

import com.rdev.tryp.model.CreateUser;
import com.rdev.tryp.model.LoginModel;
import com.rdev.tryp.model.LoginResponse;
import com.rdev.tryp.model.NearbyDriver;
import com.rdev.tryp.model.favorite_address.Data;
import com.rdev.tryp.model.favorite_address.FavoriteAddress;
import com.rdev.tryp.model.favorite_address.FavoriteAddressModel;
import com.rdev.tryp.model.favorite_address.FavoriteAddressResponse;
import com.rdev.tryp.model.sign_up_response.SignUpResponse;
import com.rdev.tryp.model.status_response.StatusResponse;
import com.rdev.tryp.model.UserPhoneNumber;
import com.rdev.tryp.model.VerifySmsResponse;
import com.rdev.tryp.model.favourite_driver.FavouriteDriver;
import com.rdev.tryp.model.ride_responce.RequestRideBody;
import com.rdev.tryp.model.ride_responce.RideResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/v1/rider_create_account")
    Call<SignUpResponse> createAccount(@Body CreateUser user);

    @POST("api/v1/send_sms")
    Call<LoginResponse> sendSms(@Body UserPhoneNumber model);

    @POST("api/v1/verify_sms")
    Call<VerifySmsResponse> verifySms(@Body LoginModel number);

    @GET("api/v1/get_nearby_drivers")
    Call<NearbyDriver> get_nearby_drivers(@Query("lat") double lat, @Query("lng") double lng);

    @GET("api/v1/get_favourite_drivers")
    Call<FavouriteDriver> get_favourite_drivers(@Query("user_id") int user_id);

    @POST("api/v1/ride_request")
    Call<RideResponse> ride_request(@Body RequestRideBody body);

    @GET("api/v1/ride_request_status")
    Call<StatusResponse> ride_request_status(@Query("ride_request_id") String id);

    @GET("api/v1/get_favorite_address")
    Call<FavoriteAddress> get_favourite_address(@Query("user_id") String user_id, @Query("type") String type);

    @POST("api/v1/add_favorite_address")
    Call<FavoriteAddressResponse> add_favorite_address(@Body FavoriteAddressModel model);

    @POST("api/v1/remove_favorite_address")
    Call<FavoriteAddressResponse> remove_favorite_address(@Body FavoriteAddressModel model);
}
