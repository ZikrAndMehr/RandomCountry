package com.zam.randomcountry.utils

object AppConstants {
    val NUMBER_RANGE = 5..20

    const val RANDOM_COUNTRY_BASE_URL = "https://random-data-api.com/api/v2/"
    const val RANDOM_COUNTRY_RESOURCE_WITH_SIZE = "addresses?size=%d"

    const val COUNTRY_INFORMATION_BASE_URL = "https://restcountries.com/v2/"
    const val COUNTRY_INFORMATION_RESOURCE_WITH_NAME = "name/%s?fields=name,capital,population,languages,flag"
}
