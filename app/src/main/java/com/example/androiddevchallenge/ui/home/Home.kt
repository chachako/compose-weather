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
package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.data.Screen
import com.example.androiddevchallenge.data.navigate
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay

/**
 * The only entry point to the HomePage UI.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun Home(navController: NavHostController, viewModel: ViewModel) {
    val state by viewModel.state
    val avatar by viewModel.avatar
    val allCityForecasts = viewModel.allCityForecasts
    val currentCityPage by viewModel.currentCityPage
    val todayForecast = viewModel.currentCityForecast
    val todayWeather = todayForecast.today

    CompositionLocalProvider(LocalHomeState provides state) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            TopBar(
                avatar = avatar,
                title = todayForecast.city.name,
                subtitle = "Updated on ${todayForecast.readableUpdateTime()}",
                trailingText = todayWeather.aqi.value.toString(),
                onMenuClick = { /*TODO*/ },
                onSearchClick = { /*TODO*/ },
                onTrailingButtonClick = { /*TODO*/ },
                isPositioned = currentCityPage == 0,
            )

            Pager(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = LocalPadding.current.vertical),
                currentCityPage = currentCityPage,
                onPageClick = viewModel::showDetails,
                onPageChanged = viewModel::changeCityPage,
                allCityForecasts = allCityForecasts,
            )
            ForecastsBar(
                hourlyForecasts = todayWeather.hours24,
                forecastDays = todayForecast.size,
                highlightColors = todayWeather.type.colors,
            )
        }
    }

    LaunchedEffect(state) {
        if (state.isExpandDetails) {
            delay(HomeTransition.MaxDuration)
            navController.navigate(
                Screen.Details.Weather,
                "cityCode" to todayForecast.city.code,
                "provinceCode" to todayForecast.city.provinceCode
            )
            viewModel.hideDetails()
        }
    }
}

/**
 * The [Home] will display different UI
 * according to this state.
 *
 * @see HomeTransition
 */
enum class HomeState {
    Initially,
    ExpandDetails;

    val isInitial get() = this == Initially
    val isExpandDetails get() = this == ExpandDetails
}

val LocalHomeState = compositionLocalOf { HomeState.Initially }
