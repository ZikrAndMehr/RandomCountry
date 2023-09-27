package com.zam.randomcountry.data.api

import com.zam.randomcountry.data.model.CountryInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RandomCountryInfoAPI {
    @GET
    fun getCountryInformation(@Url url: String): Call<ArrayList<CountryInfo>>
}