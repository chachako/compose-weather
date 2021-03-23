/*
 * Copyright 2021-2021 The Android Open Source Project
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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.data.Padding
import com.example.androiddevchallenge.data.Screen
import com.example.androiddevchallenge.ui.details.weather.WeatherDetails
import com.example.androiddevchallenge.ui.home.Home
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

/**
 * Weather App 的 UI 唯一入口点
 * The only UI entry point for the Weather App.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    ProvideWindowInsets {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onBackground,
            LocalPadding provides remember { Padding(24.dp, 24.dp) },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        Home(
                            navController = navController,
                            viewModel = hiltNavGraphViewModel()
                        )
                    }
                    composable(
                        Screen.Details.Weather.route,
                        arguments = listOf(
                            navArgument("cityCode") { type = NavType.IntType },
                            navArgument("provinceCode") { type = NavType.IntType },
                        )
                    ) {
                        // TODO: A more elegant way?
                        WeatherDetails(
                            navController = navController,
                            viewModel = hiltNavGraphViewModel(),
                            cityCode = it.arguments!!.getInt("cityCode"),
                            provinceCode = it.arguments!!.getInt("provinceCode")
                        )
                    }
                }
            }
        }
    }
}
