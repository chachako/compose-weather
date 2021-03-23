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
@file:Suppress("CanBeParameter")

package com.example.androiddevchallenge.ui.details.weather

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.repository.CitiesRepository
import com.example.androiddevchallenge.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel of weather details screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class ViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository,
) : ViewModel() {
    val state = mutableStateOf(DetailsState.Expand)

    fun geWeatherForecasts(cityCode: Int, provinceCode: Int) =
        weatherRepository.getByCity(citiesRepository.getByCode(cityCode, provinceCode))
}
