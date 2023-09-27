package com.zam.randomcountry.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zam.randomcountry.MainViewModel
import com.zam.randomcountry.R
import com.zam.randomcountry.RandomCountriesState
import com.zam.randomcountry.data.model.Country
import com.zam.randomcountry.ui.theme.RandomCountryTheme
import com.zam.randomcountry.utils.Validation
import com.zam.randomcountry.utils.ViewState
import com.zam.randomcountry.utils.composables.MyAlertDialog
import com.zam.randomcountry.utils.composables.MyButton
import com.zam.randomcountry.utils.composables.MyCircularProgressIndicator
import com.zam.randomcountry.utils.composables.MyText
import com.zam.randomcountry.utils.composables.MyTextField
import com.zam.randomcountry.utils.composables.spacer.MyVerticalSpacer
import com.zam.randomcountry.utils.navigation.Screen

@Composable
fun RandomCountries(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    RandomCountryTheme {
        Surface {
            RandomCountriesContent(
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
fun PreviewRandomCountries() {
    RandomCountryTheme {
        Surface {
            RandomCountriesContent(
                navController = rememberNavController(),
                mainViewModel = MainViewModel()
            )
        }
    }
}

@Composable
private fun RandomCountriesContent(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val randomCountries = mainViewModel.randomCountries.collectAsState()
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
            EnterNumberTextField { mainViewModel.updateCount(it) }
            MyVerticalSpacer()
            FetchDataButton { mainViewModel.fetchData() }
            MyVerticalSpacer(R.dimen.spacing_triple)
        }
        RandomCountriesList(randomCountries) {
            mainViewModel.fetchData()
        }
        if (randomCountries.value is ViewState.Success) {
            CountriesInformationButton(navController) {
                mainViewModel.countriesInformation()
            }
        }
    }
}

@Composable
private fun TitleText() {
    MyText(
        text = R.string.enter_number,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun EnterNumberTextField(updateCount: (Int?) -> Unit) {
    val errorState = remember { mutableStateOf(false) }
    val errorText = remember { mutableStateOf<Int?>(null) }
    val newCount = rememberSaveable { mutableStateOf("") }

    MyTextField(
        text = newCount.value,
        changed = {
            newCount.value = it
            if (!Validation.validateNumber(it)) {
                errorState.value = true
                errorText.value = R.string.number_range_error
            } else {
                updateCount(it.toIntOrNull())
                errorState.value = false
                errorText.value = null
            }
        },
        icon = R.drawable.ic_numbers,
        hint = R.string.number,
        errorState = errorState.value,
        errorText = errorText.value,
        keyboardType = KeyboardType.NumberPassword
    )
}

@Composable
private fun FetchDataButton(fetchData: () -> Unit) {
    MyButton(
        text = R.string.fetch_data,
        clicked = { fetchData() }
    )
}

@Composable
private fun ColumnScope.RandomCountriesList(
    randomCountriesState: State<RandomCountriesState>,
    fetchData: () -> Unit
) {
    when (val data = randomCountriesState.value) {
        is ViewState.Idle -> { }
        is ViewState.Loading -> { RandomCountriesListLoading() }
        is ViewState.Success -> {
            data.value?.let { RandomCountriesListSuccess(it) }
        }
        is ViewState.Error -> {
            RandomCountriesListError(data.throwable?.message, fetchData)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RandomCountriesListLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LocalSoftwareKeyboardController.current?.hide()
        MyCircularProgressIndicator()
    }
}

@Composable
private fun ColumnScope.RandomCountriesListSuccess(randomCountries: ArrayList<Country>) {
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.spacing_single_half)))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(dimensionResource(R.dimen.spacing_single_half))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.spacing_single_half))),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_single_half))
    ) {
        items(randomCountries) {
            CountryItem(it)
        }
    }
}

@Composable
private fun CountryItem(country: Country) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.spacing_septuple)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            width = dimensionResource(R.dimen.border_one),
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.spacing_septuple))
                    .padding(dimensionResource(R.dimen.spacing_double)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_country),
                    contentDescription = stringResource(R.string.country),
                    modifier = Modifier.fillMaxSize()
                )
            }
            MyText(
                text = country.country,
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
private fun RandomCountriesListError(error: String?, fetchData: () -> Unit) {
    val dismiss = remember { mutableStateOf(false) }
    if (!dismiss.value) {
        MyAlertDialog(
            icon = R.drawable.ic_error,
            dialogTitle = stringResource(R.string.error_fetching_data_title),
            dialogText = error ?: stringResource(R.string.error_fetching_data_message),
            dismissText = R.string.cancel,
            onDismissClicked = { dismiss.value = !dismiss.value },
            confirmText = R.string.retry,
            onConfirmClicked = {
                fetchData()
                dismiss.value = !dismiss.value
            }
        )
    }
}

@Composable
private fun CountriesInformationButton(
    navController: NavController,
    countriesInformation: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        MyVerticalSpacer(R.dimen.spacing_triple)
        MyButton(
            text = R.string.countries_information,
            clicked = {
                countriesInformation()
                navController.navigate(Screen.RANDOM_COUNTRIES_INFO.route)
            }
        )
    }
}