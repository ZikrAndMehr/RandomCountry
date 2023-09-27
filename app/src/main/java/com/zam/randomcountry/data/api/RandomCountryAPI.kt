package com.zam.randomcountry.data.api

import com.zam.randomcountry.data.model.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RandomCountryAPI {
    @GET
    fun getRandomCountries(@Url url: String): Call<ArrayList<Country>>
}