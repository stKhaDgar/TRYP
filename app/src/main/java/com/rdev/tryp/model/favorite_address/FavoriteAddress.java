package com.rdev.tryp.model.favorite_address;

import com.google.gson.annotations.SerializedName;

public class FavoriteAddress {

        @SerializedName("data")
        private Data data;

        public void setData(Data data){
            this.data = data;
        }

        public Data getData(){
            return data;
        }

        @Override
        public String toString(){
            return
                    "FavoriteAddress{" +
                            "data = '" + data + '\'' +
                            "}";
        }
}
