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

package com.example.androiddevchallenge.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.WeatherForecasts
import com.example.androiddevchallenge.repository.CitiesRepository
import com.example.androiddevchallenge.repository.ProfileRepository
import com.example.androiddevchallenge.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 用于管理主页屏幕的数据
 * The ViewModel of home screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class ViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    /** Weather forecasts for all cities. */
    val allCityForecasts = listOf(weatherRepository.getByLocation())
        .plus(citiesRepository.getAllAdd().map(weatherRepository::getByCity))
        .toMutableStateList()

    /** The profile picture */
    val avatar = mutableStateOf(profileRepository.getAvatar())

    /** Represents the current [Home] state, related to the navigation transition. */
    val state = mutableStateOf(HomeState.Initially)

    /** Weather page of the current city in the [Home] pager. */
    val currentCityPage = mutableStateOf(0)

    /** Weather forecasts of the current city in the [Home] pager. */
    val currentCityForecast: WeatherForecasts get() = allCityForecasts[currentCityPage.value]

    /** Change the city page index of [Home] pager. */
    fun changeCityPage(index: Int) {
        currentCityPage.value = index
    }

    /**
     * 显示当前城市天气的详细信息
     */
    fun showDetails() {
        state.value = HomeState.ExpandDetails
    }

    /**
     * 隐藏天气详情以回到主页
     */
    fun hideDetails() {
        state.value = HomeState.Initially
    }
}
