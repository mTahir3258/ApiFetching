package com.example.machinetest.api

import com.example.machinetest.model.PopulationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestApi {
    @GET("data")
    fun fetchData(
        @Query("drilldowns") drillDown: String,
        @Query("measures") population: String
    ): Call<PopulationResponse>

}