package com.rdev.tryp.model.favorite_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("favorite_addresses")
    @Expose
    private FavoriteAddresses favoriteAddresses;

    public FavoriteAddresses getFavoriteAddresses() {
        return favoriteAddresses;
    }

    public void setFavoriteAddresses(FavoriteAddresses favoriteAddresses) {
        this.favoriteAddresses = favoriteAddresses;
    }

}
