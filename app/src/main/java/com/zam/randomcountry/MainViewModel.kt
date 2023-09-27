/*
 * Copyright (C) 2023 Zokirjon Mamadjonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zam.randomcountry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zam.randomcountry.data.api.RandomCountryInfoAPI
import com.zam.randomcountry.data.api.RandomCountryAPI
import com.zam.randomcountry.data.model.Country
import com.zam.randomcountry.data.model.CountryInfo
import com.zam.randomcountry.utils.AppConstants
import com.zam.randomcountry.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

typealias RandomCountriesState = ViewState<ArrayList<Country>>
typealias RandomCountriesInfoState = ViewState<Map<Country, CountryInfo?>>

class MainViewModel : ViewModel() {
    private var count: Int? = null

    private val _randomCountries = MutableStateFlow<RandomCountriesState>(ViewState.Idle())
    val randomCountries = _randomCountries.asStateFlow()

    private var randomCountriesList = arrayListOf<Country>()

    private val _randomCountriesInfo = MutableStateFlow<RandomCountriesInfoState>(ViewState.Idle())
    val randomCountriesInfo = _randomCountriesInfo.asStateFlow()

    private var randomCountriesInfoList = mutableMapOf<Country, CountryInfo?>()

    fun updateCount(count: Int?) {
        this.count = count
    }

    fun fetchData() {
        count?.let {
            randomCountriesInfoList = mutableMapOf()
            _randomCountries.tryEmit(ViewState.Loading())

            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.RANDOM_COUNTRY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val randomCountryAPI = retrofit.create(RandomCountryAPI::class.java)
            val url = AppConstants.RANDOM_COUNTRY_BASE_URL +
                    String.format(AppConstants.RANDOM_COUNTRY_RESOURCE_WITH_SIZE, it)
            val call = randomCountryAPI.getRandomCountries(url)

            call.enqueue(object : Callback<ArrayList<Country>> {
                override fun onResponse(
                    call: Call<ArrayList<Country>>,
                    response: Response<ArrayList<Country>>
                ) {
                    if (response.isSuccessful) {
                        randomCountriesList = response.body() as ArrayList<Country>
                        randomCountriesList.sortBy { country ->  country.country }
                        _randomCountries.tryEmit(
                            ViewState.Success(randomCountriesList)
                        )
                    } else {
                        _randomCountries.tryEmit(
                            ViewState.Error(error = response.errorBody().toString())
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<Country>>,
                    throwable: Throwable
                ) {
                    _randomCountries.tryEmit(
                        ViewState.Error(throwable = throwable)
                    )
                }
            })
        }
    }

    fun countriesInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            if (randomCountriesInfoList.isEmpty()) {
                fetchRandomCountriesInfo()
            } else {
                _randomCountriesInfo.emit(
                    ViewState.Success(randomCountriesInfoList)
                )
            }
        }
    }

    private suspend fun fetchRandomCountriesInfo() {
        _randomCountriesInfo.emit(ViewState.Loading())

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.COUNTRY_INFORMATION_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        randomCountriesList.forEach { country ->
            val randomCountryAPI = retrofit.create(RandomCountryInfoAPI::class.java)
            val url = AppConstants.COUNTRY_INFORMATION_BASE_URL +
                    String.format(AppConstants.COUNTRY_INFORMATION_RESOURCE_WITH_NAME, country.country)
            val call = randomCountryAPI.getCountryInformation(url)

            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val countryInfo = response.body()?.get(0) as CountryInfo
                    randomCountriesInfoList[country] = countryInfo
                } else {
                    randomCountriesInfoList[country] = null
                }
            } catch (e: Exception) {
                _randomCountriesInfo.emit(
                    ViewState.Error(throwable = e)
                )
            }
        }

        randomCountriesInfoList = randomCountriesInfoList.toSortedMap(compareBy { it.country })
        _randomCountriesInfo.emit(
            ViewState.Success(randomCountriesInfoList)
        )
    }
}