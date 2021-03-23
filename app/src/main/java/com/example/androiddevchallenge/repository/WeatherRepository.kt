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
package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.data.City
import com.example.androiddevchallenge.data.WeatherForecasts
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides weather data.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Singleton
class WeatherRepository @Inject constructor(
    citiesRepository: CitiesRepository,
    private val mockRepository: MockRepository,
) {
    private val currentCity = citiesRepository.getLocation()

    /** 根据定位获取天气预报 */
    fun getByLocation() = getByCity(currentCity)

    /** 根据城市获取天气预报 */
    fun getByCity(city: City): WeatherForecasts = mockRepository.geWeatherForecasts(city)
}
