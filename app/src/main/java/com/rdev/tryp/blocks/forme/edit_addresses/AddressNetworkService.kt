package com.rdev.tryp.blocks.forme.edit_addresses

import android.util.Log

import com.google.android.gms.maps.model.LatLng
import com.rdev.tryp.intro.manager.AccountManager
import com.rdev.tryp.model.TripPlace
import com.rdev.tryp.model.favorite_address.FavoriteAddress
import com.rdev.tryp.model.favorite_address.FavoriteAddressModel
import com.rdev.tryp.model.favorite_address.FavoriteAddressResponse
import com.rdev.tryp.network.NetworkService
import com.rdev.tryp.utils.PreferenceManager
import com.rdev.tryp.utils.Utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.rdev.tryp.utils.Utils.KEY_HOME
import com.rdev.tryp.utils.Utils.KEY_WORK


object AddressNetworkService {

    fun saveAddresses(newHome: TripPlace?, newWork: TripPlace?) {
        setHomeAddress(newHome)
        setWorkAddress(newWork)
    }

    private fun setHomeAddress(newAddress: TripPlace?) {
        val model = FavoriteAddressModel()
        val lastHome = PreferenceManager.getTripPlace(KEY_HOME)

        if (newAddress == null) {
            return
        }

        if (lastHome != null) {
            model.address = lastHome.locale
            model.lat = lastHome.coord?.latitude.toString()
            model.lng = lastHome.coord?.longitude.toString()
            model.type = Utils.HOME_ADDRESS
            model.userId = AccountManager.getInstance()?.userId

            NetworkService.getApiService().remove_favorite_address(model).enqueue(object : Callback<FavoriteAddressResponse> {
                override fun onResponse(call: Call<FavoriteAddressResponse>, response: Response<FavoriteAddressResponse>) {
                    val body = response.body()
                    if (body != null) {
                        Log.i("remove home address", model.address)
                    }
                }

                override fun onFailure(call: Call<FavoriteAddressResponse>, t: Throwable) {
                    Log.i("remove home address", "failure")
                }
            })
        }

        if (lastHome == null || newAddress.locale == lastHome.locale) {
            if (newAddress.locale == null) {
                return
            }

            model.address = newAddress.locale
            model.lat = newAddress.coord?.latitude.toString()
            model.lng = newAddress.coord?.longitude.toString()
            model.type = Utils.HOME_ADDRESS
            model.userId = AccountManager.getInstance()?.userId

            NetworkService.getApiService().add_favorite_address(model).enqueue(object : Callback<FavoriteAddressResponse> {
                override fun onResponse(call: Call<FavoriteAddressResponse>, response: Response<FavoriteAddressResponse>) {
                    val body = response.body()
                    if (body != null) {
                        Log.i("add home address", model.address)
                    }
                }

                override fun onFailure(call: Call<FavoriteAddressResponse>, t: Throwable) {
                    Log.i("add home address", "failure")
                }
            })
        }

        PreferenceManager.setTripPlace(KEY_HOME, newAddress)
    }

    private fun setWorkAddress(newAddress: TripPlace?) {
        val model = FavoriteAddressModel()
        val lastWork = PreferenceManager.getTripPlace(KEY_WORK)

        if (newAddress == null) {
            return
        }

        if (lastWork != null) {
            model.address = lastWork.locale
            model.lat = lastWork.coord?.latitude.toString()
            model.lng = lastWork.coord?.longitude.toString()
            model.type = Utils.WORK_ADDRESS
            model.userId = AccountManager.getInstance()?.userId

            NetworkService.getApiService().remove_favorite_address(model).enqueue(object : Callback<FavoriteAddressResponse> {
                override fun onResponse(call: Call<FavoriteAddressResponse>, response: Response<FavoriteAddressResponse>) {
                    val body = response.body()
                    if (body != null) {
                        Log.i("remove work address", model.address)
                    }
                }

                override fun onFailure(call: Call<FavoriteAddressResponse>, t: Throwable) {
                    Log.i("remove work address", "failure")
                }
            })
        }

        if (lastWork == null || newAddress.locale == lastWork.locale) {
            if (newAddress.locale == null) {
                return
            }
            model.address = newAddress.locale
            model.lat = newAddress.coord?.latitude.toString()
            model.lng = newAddress.coord?.longitude.toString()
            model.type = Utils.WORK_ADDRESS
            model.userId = AccountManager.getInstance()?.userId

            NetworkService.getApiService().add_favorite_address(model).enqueue(object : Callback<FavoriteAddressResponse> {
                override fun onResponse(call: Call<FavoriteAddressResponse>, response: Response<FavoriteAddressResponse>) {
                    val body = response.body()
                    if (body != null) {
                        Log.i("add work address", model.address)
                    }
                }

                override fun onFailure(call: Call<FavoriteAddressResponse>, t: Throwable) {
                    Log.i("add work address", "failure")
                }
            })
        }
        PreferenceManager.setTripPlace(KEY_WORK, newAddress)
    }

    fun initFavoriteAddresses() {
        setUpHomeAddress()
        setUpWorkAddress()
    }

    private fun setUpWorkAddress() {
        AccountManager.getInstance()?.userId?.let { userId ->
            NetworkService.getApiService().get_favourite_address(userId, Utils.WORK_ADDRESS).enqueue(object : Callback<FavoriteAddress> {
                override fun onResponse(call: Call<FavoriteAddress>, response: Response<FavoriteAddress>) {
                    if (response.body()?.data?.favoriteAddresses == null) {
                        return
                    }
                    val address = response.body()?.data?.favoriteAddresses?.address ?: return
                    val tripPlace = TripPlace()
                    tripPlace.locale = address.address
                    address.lat?.let { lat ->
                        address.lng?.let { lng ->
                            tripPlace.coord = LatLng(lat.toDouble(), lng)
                        }
                    }
                    PreferenceManager.setTripPlace(KEY_WORK, tripPlace)
                    Log.i("get work address", address.address)
                }

                override fun onFailure(call: Call<FavoriteAddress>, t: Throwable) {
                    Log.i("get work address", "failure")
                }
            })
        }
    }

    private fun setUpHomeAddress() {
        AccountManager.getInstance()?.userId?.let { userId ->
            NetworkService.getApiService().get_favourite_address(userId, Utils.HOME_ADDRESS).enqueue(object : Callback<FavoriteAddress> {
                override fun onResponse(call: Call<FavoriteAddress>, response: Response<FavoriteAddress>) {
                    if (response.body()?.data?.favoriteAddresses == null) {
                        return
                    }
                    val address = response.body()?.data?.favoriteAddresses?.address ?: return
                    val tripPlace = TripPlace()
                    tripPlace.locale = address.address
                    address.lat?.let { lat ->
                        address.lng?.let { lng ->
                            tripPlace.coord = LatLng(lat.toDouble(), lng)
                        }
                    }
                    PreferenceManager.setTripPlace(KEY_HOME, tripPlace)
                    Log.i("get home address", address.address)
                }

                override fun onFailure(call: Call<FavoriteAddress>, t: Throwable) {
                    Log.i("get home address", "failure")
                }
            })
        }
    }

}