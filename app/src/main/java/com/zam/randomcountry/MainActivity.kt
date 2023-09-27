package com.zam.randomcountry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zam.randomcountry.ui.screens.RandomCountries
import com.zam.randomcountry.ui.screens.RandomCountriesInfo
import com.zam.randomcountry.utils.navigation.Screen
import com.zam.randomcountry.ui.theme.RandomCountryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomCountryTheme {
                Surface {
                    MainActivityContent()
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewMainActivity() {
    RandomCountryTheme {
        Surface {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    val mainViewModel: MainViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.RANDOM_COUNTRIES.route
    ) {
        composable(Screen.RANDOM_COUNTRIES.route) {
            RandomCountries(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(Screen.RANDOM_COUNTRIES_INFO.route) {
            RandomCountriesInfo(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
    }
}