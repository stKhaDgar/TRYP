package com.rdev.tryp.network

import com.rdev.tryp.model.CreateUser
import com.rdev.tryp.model.LoginModel
import com.rdev.tryp.model.LoginResponse
import com.rdev.tryp.model.NearbyDriver
import com.rdev.tryp.model.favorite_address.FavoriteAddress
import com.rdev.tryp.model.favorite_address.FavoriteAddressModel
import com.rdev.tryp.model.favorite_address.FavoriteAddressResponse
import com.rdev.tryp.model.sign_up_response.SignUpResponse
import com.rdev.tryp.model.status_response.StatusResponse
import com.rdev.tryp.model.UserPhoneNumber
import com.rdev.tryp.model.login_response.VerifySmsResponse
import com.rdev.tryp.model.favourite_driver.FavouriteDriver
import com.rdev.tryp.model.ride_responce.RequestRideBody
import com.rdev.tryp.model.ride_responce.RideResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST("api/v1/rider_create_account")
    fun createAccount(@Body user: CreateUser): Call<SignUpResponse>

    @POST("api/v1/send_sms")
    fun sendSms(@Body model: UserPhoneNumber): Call<LoginResponse>

    @POST("api/v1/verify_sms")
    fun verifySms(@Body number: LoginModel): Call<VerifySmsResponse>

    @GET("api/v1/get_nearby_drivers")
    fun get_nearby_drivers(@Query("lat") lat: Double, @Query("lng") lng: Double): Call<NearbyDriver>

    @GET("api/v1/get_favourite_drivers")
    fun get_favourite_drivers(@Query("user_id") user_id: Int): Call<FavouriteDriver>

    @POST("api/v1/ride_request")
    fun ride_request(@Body body: RequestRideBody): Call<RideResponse>

    @GET("api/v1/ride_request_status")
    fun ride_request_status(@Query("user_id") user_id: Int, @Query("ride_request_id") id: String): Call<StatusResponse>

    @GET("api/v1/get_favorite_address")
    fun get_favourite_address(@Query("user_id") user_id: Int, @Query("type") type: String): Call<FavoriteAddress>

    @POST("api/v1/add_favorite_address")
    fun add_favorite_address(@Body model: FavoriteAddressModel): Call<FavoriteAddressResponse>

    @POST("api/v1/remove_favorite_address")
    fun remove_favorite_address(@Body model: FavoriteAddressModel): Call<FavoriteAddressResponse>
}
