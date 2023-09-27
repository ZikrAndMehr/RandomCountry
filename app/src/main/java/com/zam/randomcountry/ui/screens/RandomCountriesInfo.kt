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

package com.zam.randomcountry.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.zam.randomcountry.MainViewModel
import com.zam.randomcountry.R
import com.zam.randomcountry.RandomCountriesInfoState
import com.zam.randomcountry.data.model.Country
import com.zam.randomcountry.data.model.CountryInfo
import com.zam.randomcountry.ui.theme.RandomCountryTheme
import com.zam.randomcountry.utils.ViewState
import com.zam.randomcountry.utils.composables.MyCircularProgressIndicator
import com.zam.randomcountry.utils.composables.MyText
import com.zam.randomcountry.utils.composables.spacer.MyHorizontalSpacer
import com.zam.randomcountry.utils.composables.spacer.MyVerticalSpacer

@Composable
fun RandomCountriesInfo(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    RandomCountryTheme {
        Surface {
            RandomCountriesInfoContent(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewRandomInfoCountries() {
    RandomCountryTheme {
        Surface {
            RandomCountriesInfoContent(
                navController = rememberNavController(),
                mainViewModel = MainViewModel()
            )
        }
    }
}

@Composable
private fun RandomCountriesInfoContent(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_triple))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText()
            MyVerticalSpacer(R.dimen.spacing_double)
        }
        RandomCountriesInfoList(mainViewModel.randomCountriesInfo.collectAsState())
    }
}

@Composable
private fun TitleText() {
    MyText(
        text = R.string.countries_information,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun RandomCountriesInfoList(randomCountriesInfoState: State<RandomCountriesInfoState>) {
    when (val data = randomCountriesInfoState.value) {
        is ViewState.Loading -> { RandomCountriesInfoListLoading() }
        is ViewState.Success -> {
            data.value?.let { RandomCountriesInfoListSuccess(it) }
        }
        else -> { }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RandomCountriesInfoListLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LocalSoftwareKeyboardController.current?.hide()
        MyCircularProgressIndicator()
    }
}

@Composable
private fun RandomCountriesInfoListSuccess(
    randomCountriesInfo: Map<Country, CountryInfo?>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.spacing_single_half)))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(dimensionResource(R.dimen.spacing_single_half))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.spacing_single_half))),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_single_half))
    ) {
        items(items = randomCountriesInfo.entries.toList()) {
            CountryInfoItem(it)
        }
    }
}

@Composable
private fun CountryInfoItem(randomCountriesInfo: Map.Entry<Country, CountryInfo?>) {
    val country = randomCountriesInfo.key
    val countryInfo = randomCountriesInfo.value
    val expanded = remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            width = dimensionResource(R.dimen.border_one),
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_double))
        ) {
            countryInfo?.let {
                CountryInfoItemMainContent(expanded, country, it)
                if (expanded.value) {
                    CountryInfoItemExpandedContent(it)
                }
            } ?: run {
                MyText(
                    text = stringResource(
                        id = R.string.countries_information_null,
                        formatArgs = arrayOf(country.country)
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CountryInfoItemMainContent(
    expanded: MutableState<Boolean>,
    country: Country,
    countryInfo: CountryInfo
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(countryInfo.flag)
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = stringResource(R.string.flag),
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.spacing_double))
                .size(dimensionResource(R.dimen.spacing_triple))
                .align(Alignment.CenterVertically)
        )
        MyText(
            text = country.country,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        MyHorizontalSpacer(R.dimen.spacing_triple)
        Icon(
            painter = if (expanded.value) {
                painterResource(R.drawable.ic_arrow_down)
            } else {
                painterResource(R.drawable.ic_arrow_right)
            },
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.spacing_quadruple))
                .clickable { expanded.value = !expanded.value }
        )
    }
}

@Composable
private fun CountryInfoItemExpandedContent(countryInfo: CountryInfo) {
    MyVerticalSpacer(R.dimen.spacing_single)
    Column(modifier = Modifier.fillMaxWidth()) {
        PairOfText(
            boldText = R.string.name,
            text = countryInfo.name
        )
        PairOfText(
            boldText = R.string.capital,
            text = countryInfo.capital
        )
        PairOfText(
            boldText = R.string.population,
            text = countryInfo.population.toString()
        )
        val language = countryInfo.languages.first()
        PairOfText(
            boldText = R.string.languages,
            text = stringResource(
                id = R.string.languages_value,
                formatArgs = arrayOf(
                    language.iso1,
                    language.iso2,
                    language.name,
                    language.nativeName
                )
            )
        )
    }
}

@Composable
private fun PairOfText(
    @StringRes boldText: Int,
    text: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        MyText(
            text = boldText,
            modifier = Modifier
                .width(130.dp)
                .padding(horizontal = dimensionResource(R.dimen.spacing_double)),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        MyText(
            text = text,
            modifier = Modifier,
            textAlign = TextAlign.Start
        )
    }
}