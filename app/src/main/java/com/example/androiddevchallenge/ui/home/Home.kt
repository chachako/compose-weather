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

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.util.observeAsNonNullState
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

/**
 * The only entry point to the HomePage UI.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun Home(navController: NavHostController, viewModel: ViewModel) {
  val avatar by viewModel.avatar.observeAsNonNullState()
  val allCityWeathers by viewModel.allCityWeathers.observeAsNonNullState()
  val weatherIndex by viewModel.currentWeatherIndex.observeAsNonNullState()
  val weather = viewModel.currentWeather
  val weatherInfo = weather.today

  Column(
    modifier = Modifier
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    TopBar(
      title = weather.city.name,
      subtitle = "Updated on ${weather.updateTime}",
      avatar = avatar,
      trailingText = weatherInfo.aqi.value.toString(),
      onMenuClick = { /*TODO*/ },
      onSearchClick = { /*TODO*/ },
      onTrailingButtonClick = { /*TODO*/ },
      isPositioned = weatherIndex == 0,
    )
    Summary(
      modifier = Modifier
        .weight(1f)
        .padding(top = LocalPadding.current.vertical),
      allCityWeathers = allCityWeathers,
      currentPage = weatherIndex,
      onPageChanged = { viewModel.selectCityWeather(it) }
    )
    Spacer(modifier = Modifier.height(LocalPadding.current.vertical))
    Period(
      hourlyForecast = weatherInfo.hours,
      forecastDays = weather.forecastDays.size,
      highlightColors = weatherInfo.kind.colors,
    )
  }
}
