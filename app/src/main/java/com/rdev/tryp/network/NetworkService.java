package com.rdev.tryp.network;

public class NetworkService {
    private static ApiService apiService;
    public static ApiService getApiService(){
        if(apiService == null){
            apiService = ApiClient.getInstance().create(ApiService.class);
        }
        return apiService;
    }
}
