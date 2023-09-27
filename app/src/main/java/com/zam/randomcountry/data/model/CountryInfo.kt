package com.zam.randomcountry.data.model

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    val name: String,
    val capital: String,
    val population: Long,
    val languages: List<Language>,
    val flag: String,
    val independent: Boolean
) {
    data class Language(
        @SerializedName("iso639_1") val iso1: String,
        @SerializedName("iso639_2") val iso2: String,
        val name: String,
        val nativeName: String
    )
}
