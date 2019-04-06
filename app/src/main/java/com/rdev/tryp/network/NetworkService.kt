package com.rdev.tryp.network


object NetworkService {

    private var apiService: ApiService? = null


    fun getApiService(): ApiService? {
        if (apiService == null) {
            apiService = ApiClient.getInstance()?.create(ApiService::class.java)
        }
        return apiService
    }

}