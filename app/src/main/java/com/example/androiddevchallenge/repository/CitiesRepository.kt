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

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.example.androiddevchallenge.data.City
import com.meowbase.toolkit.other.logError
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.buffer
import okio.source
import javax.inject.Inject

/**
 * Provides cities data.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@OptIn(ExperimentalStdlibApi::class)
class CitiesRepository @Inject constructor(
  moshi: Moshi,
  assets: AssetManager,
  private val dataStore: SharedPreferences,
  private val mockRepository: MockRepository
) {
  private val cities: List<City> by lazy {
    val json = assets.open("cities.json").source().buffer()
    moshi.adapter<List<City>>().fromJson(json)!!
  }

  private var currentCity = mockRepository.getCityByLocation(cities)

  /** 获得当前定位的城市 */
  fun getLocation(): City {
    currentCity = mockRepository.getCityByLocation(cities)
    return currentCity
  }

  /** 获得手动添加的所有城市 */
  fun getAllAdd(): Set<City> {
    val result = mutableSetOf<City>()
    while (result.isEmpty() || result.any { it == currentCity } || result.size < 3) {
      val index = cities.indices.random()
      result.add(cities[index])
    }
    return result
  }
}
