/*
 * Copyright 2021 The Android Open Source Project
 * Copyright 2021 RinOrz (凛)
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
 * Github home page: https://github.com/RinOrz
 */
package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.data.City
import com.example.androiddevchallenge.data.Weather
import javax.inject.Inject

/**
 * Provides weather data.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
class WeatherRepository @Inject constructor(
  citiesRepository: CitiesRepository,
  private val mockRepository: MockRepository,
) {
  private val currentCity = citiesRepository.getLocation()

  /** 根据定位获取天气 */
  fun getByLocation() = getByCity(currentCity)

  /** 根据城市获取天气 */
  fun getByCity(city: City): Weather = mockRepository.getWeather(city)
}
