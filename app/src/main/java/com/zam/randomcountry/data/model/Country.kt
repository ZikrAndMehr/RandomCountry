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

package com.zam.randomcountry.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    val id: Int,
    val uid: String,
    val city: String,
    @SerializedName("street_name") val streetName: String,
    @SerializedName("street_address") val streetAddress: String,
    @SerializedName("secondary_address") val secondaryAddress: String,
    @SerializedName("building_number") val buildingNumber: String,
    @SerializedName("mail_box") val mailBox: String,
    val community: String,
    @SerializedName("zip_code") val zipCode: String,
    val zip: String,
    val postcode: String,
    @SerializedName("time_zone") val timeZone: String,
    @SerializedName("street_suffix") val streetSuffix: String,
    @SerializedName("city_suffix") val citySuffix: String,
    @SerializedName("city_prefix") val cityPrefix: String,
    val state: String,
    @SerializedName("state_abbr") val stateAbbr: String,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("full_address") val fullAddress: String
)

