/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.details.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.R
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlin.math.roundToInt

/**
 * The only entry point to the HomePage UI.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherDetails(
    navController: NavHostController,
    viewModel: ViewModel,
    cityCode: Int,
    provinceCode: Int
) {
    val state by viewModel.state
    val forecast = viewModel.geWeatherForecasts(cityCode, provinceCode)
    val weather = forecast.today
    CompositionLocalProvider(LocalWeatherDetailsState provides state) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Board(
                temperature = weather.temperature.realtime,
                title = weather.type.name,
                subtitle = forecast.city.name,
                trailingText = weather.aqi.value.toString(),
                colors = weather.type.colors,
                icon = weather.type.icon,
                onTrailingButtonClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(12.dp))
            GridRow {
                GridItem(
                    icon = R.drawable.ic_humidity,
                    subtitle = "Humidity",
                    title = weather.humidity.roundToInt().toString(),
                    unit = "%",
                    progress = weather.humidity,
                    maxProgress = 100f,
                    progressColors = weather.type.colors
                )
                GridItem(
                    icon = R.drawable.ic_visibility,
                    subtitle = "Visibility",
                    title = weather.visibility.roundToInt().toString(),
                    unit = "km",
                    progress = weather.visibility,
                    maxProgress = 40f,
                    progressColors = weather.type.colors
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            GridRow {
                GridItem(
                    icon = R.drawable.ic_wind,
                    subtitle = "Wind",
                    title = weather.wind.roundToInt().toString(),
                    unit = "km/h",
                    progress = weather.wind,
                    maxProgress = 150f,
                    progressColors = weather.type.colors
                )
                GridItem(
                    icon = R.drawable.ic_pressure,
                    subtitle = "Pressure",
                    title = weather.pressure.roundToInt().toString(),
                    unit = "hPa",
                    progress = weather.pressure,
                    maxProgress = 1100f,
                    progressColors = weather.type.colors
                )
            }
            // TODO
//    SunriseSunset()
        }
    }
}

/**
 * The [WeatherDetails] will display different UI
 * according to this state.
 *
 * @see DetailsTransition
 */
enum class DetailsState {
    Expand,
    Collapse;

    val isExpand get() = this == Expand
    val isCollapse get() = this == Collapse
}

val LocalWeatherDetailsState = compositionLocalOf { DetailsState.Expand }
