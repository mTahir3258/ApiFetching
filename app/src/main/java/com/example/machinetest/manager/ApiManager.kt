package com.example.machinetest.manager

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {

    companion object {

        private val BASE_URL: String = "https://datausa.io/api/"

        private var retrofit: Retrofit? = null

        fun <S> getClient(serviceClass: Class<S>): S {
            val client = OkHttpClient.Builder()
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(serviceClass)
        }

    }
}