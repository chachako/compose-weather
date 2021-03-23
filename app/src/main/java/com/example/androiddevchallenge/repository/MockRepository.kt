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
package com.example.androiddevchallenge.repository

import android.content.res.AssetManager
import com.example.androiddevchallenge.data.AQI
import com.example.androiddevchallenge.data.City
import com.example.androiddevchallenge.data.DayWeather
import com.example.androiddevchallenge.data.HourlyWeather
import com.example.androiddevchallenge.data.Temperature
import com.example.androiddevchallenge.data.WeatherForecasts
import com.example.androiddevchallenge.data.WeatherType
import com.meowbase.toolkit.getNextHour
import com.meowbase.toolkit.int
import com.meowbase.toolkit.isToday
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okio.buffer
import okio.source
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Providers all mock data to the compose-weather application.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Singleton
@OptIn(ExperimentalStdlibApi::class)
class MockRepository @Inject constructor(
    moshi: Moshi,
    assets: AssetManager,
) {
    private val cities: List<City> by lazy {
        val json = assets.open("cities.json").source().buffer()
        moshi.adapter<List<City>>().fromJson(json)!!
    }

    private val cacheForecasts = hashMapOf<City, WeatherForecasts>()

    val locationCity by lazy {
        cities[cities.indices.random()]
    }

    val customCities by lazy {
        val result = mutableSetOf<City>()
        while (result.isEmpty() || result.any { it == locationCity } || result.size < 3) {
            val index = cities.indices.random()
            result.add(cities[index])
        }
        result
    }

    fun findByCode(cityCode: Int, provinceCode: Int?): City? = cities.find {
        if (provinceCode != null && it.provinceCode != provinceCode) return@find false
        it.code == cityCode
    }

    fun geWeatherForecasts(city: City) = cacheForecasts.getOrPut(city) {
        WeatherForecasts(
            city = city,
            forecast = listOf(getDayWeather()) + getFutureWeatherForecasts(),
            updateTime = System.currentTimeMillis(),
        )
    }

    private fun getDayWeather(): DayWeather = DayWeather(
        aqi = getAQI(),
        type = getWeatherType(),
        temperature = getTemperature(),
        hours24 = getInNext24Hours(),
        wind = Random.nextDouble(1.0, 117.0).toFloat(),
        humidity = Random.nextDouble(0.0, 100.0).toFloat(),
        pressure = Random.nextDouble(900.0, 1100.0).toFloat(),
        visibility = Random.nextDouble(1.0, 30.0).toFloat(),
        sunrise = getTime(4..7),
        sunset = getTime(17..20),
    )

    private fun getTime(hourRange: IntRange): Long {
        val hour = hourRange.random()
        val minute = (0..60).random()
        return SimpleDateFormat("H:m", Locale.getDefault()).parse("$hour:$minute")!!.time
    }

    private fun getFutureWeatherForecasts(): List<DayWeather> = mutableListOf<DayWeather>().apply {
        // 模拟接下来七天的天气
        repeat(7) { this += getDayWeather() }
    }

    private fun getInNext24Hours(): List<HourlyWeather> {
        val hours = mutableListOf<HourlyWeather>()
        val baseTemperature = getTemperature()

        // 模拟时段的预报
        fun getForecastInfo(time: Long): HourlyWeather {
            val temperatureDiff = if (time.isToday) {
                (0..6).random() - 3
            } else {
                (0..12).random() - 6
            }
            return HourlyWeather(
                aqi = getAQI(),
                type = getWeatherType(),
                // 模拟 24 小时内的细微温差
                temperature = baseTemperature.copy(realtime = baseTemperature.realtime + temperatureDiff),
                timestamp = time
            )
        }

        repeat(24) { hour ->
            hours += getForecastInfo(time = getNextHour(hour))
        }
        return hours
    }

    private fun getTemperature(): Temperature {
        val base = (100..152).random()
        val min = Random.nextDouble(base * 0.8, base * 0.96)
        val max = Random.nextDouble(base * 1.12, base * 1.15)
        val realtime = Random.nextDouble(min, max)
        return Temperature(
            realtime = realtime - 126,
            max = max - 126,
            min = min - 126
        )
    }

    private fun getWeatherType() = when ((0..7).random()) {
        0 -> WeatherType.Sunny
        1 -> WeatherType.Cloudy
        2 -> WeatherType.Shower
        3 -> WeatherType.Thundershower
        4 -> WeatherType.Snow
        5 -> WeatherType.Fog
        6 -> WeatherType.Sandstorm
        7 -> WeatherType.Haze
        else -> TODO("未知的天气类型")
    }

    private fun getAQI(): AQI {
        val index = (0..310).random()
        val pm25 = ((index / 2)..index + 80).random()
        val pm10 = ((index / 3)..((index * 0.88).int)).random()
        val no2 = ((index / 6)..(index / 3)).random()
        val so2 = (0..(index / 10)).random()
        val co = (0..(index / 12)).random()
        val o3 = (0..index).random()

        return AQI(index, pm25, pm10, so2, no2, co, o3)
    }
}
