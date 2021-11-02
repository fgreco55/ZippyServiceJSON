package com.example.zippyservice.network

import com.squareup.moshi.Json


data class ZipcodeResponse (
    @Json(name = "post code")
    val zipcode: String,

    @Json(name = "country")
    val country: String,

    @Json(name = "places")      // This is a nested JSON object (see below)
    val places: List<Place>
)

/*
 Place is a nested JSON object within ZipcodeResponse... why we need another data class
 */
data class Place (
    @Json(name = "place name")
    val place_name: String,

    val longitude: String,
    val state: String,

    @Json(name = "state abbreviation")
    val state_abbreviation: String,

    val latitude: String
)
