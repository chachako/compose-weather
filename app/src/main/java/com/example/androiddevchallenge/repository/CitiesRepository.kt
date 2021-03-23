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

import android.content.SharedPreferences
import com.example.androiddevchallenge.data.City
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides cities data.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Singleton
class CitiesRepository @Inject constructor(
    private val dataStore: SharedPreferences,
    private val mockRepository: MockRepository
) {
    fun findByCode(cityCode: Int, provinceCode: Int? = null): City? = mockRepository.findByCode(cityCode, provinceCode)
    fun getByCode(cityCode: Int, provinceCode: Int? = null): City = findByCode(cityCode, provinceCode)!!

    /** 获得当前定位的城市 */
    fun getLocation(): City = mockRepository.locationCity

    /** 获得手动添加的所有城市 */
    fun getAllAdd(): Set<City> = mockRepository.customCities
}
