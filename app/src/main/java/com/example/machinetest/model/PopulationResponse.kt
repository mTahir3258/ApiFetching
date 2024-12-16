package com.example.machinetest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PopulationResponse(
    val data: List<Population>
)

data class Population(
    @SerializedName("ID Nation")
    val IDNation: String,
    @SerializedName("Nation")
    val Nation: String,
    @SerializedName("ID Year")
    val IDYear: Int,
    @SerializedName("Year")
    val Year: String,
    @SerializedName("Population")
    var Population: Long,
    @SerializedName("Slug Nation")
    val SlugNation: String
):Serializable
