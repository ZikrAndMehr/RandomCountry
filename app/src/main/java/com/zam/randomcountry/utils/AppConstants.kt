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

package com.zam.randomcountry.utils

object AppConstants {
    val NUMBER_RANGE = 5..20

    const val RANDOM_COUNTRY_BASE_URL = "https://random-data-api.com/api/v2/"
    const val RANDOM_COUNTRY_RESOURCE_WITH_SIZE = "addresses?size=%d"

    const val COUNTRY_INFORMATION_BASE_URL = "https://restcountries.com/v2/"
    const val COUNTRY_INFORMATION_RESOURCE_WITH_NAME = "name/%s?fields=name,capital,population,languages,flag"
}
