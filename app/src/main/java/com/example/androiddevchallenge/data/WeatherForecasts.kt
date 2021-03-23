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
package com.example.androiddevchallenge.data

import com.meowbase.toolkit.formatTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * A city weather forecasts for the present and future.
 *
 * @author 凛 (https://github.com/RinOrz)
 *
 * @param city Represents the data comes from this city’s forecast
 * @param updateTime Timestamp of the last update time of the data
 * @param forecast All weather forecasts for today and the future
 *
 * ```
 * val data: WeatherForecasts
 * // Fetch to tomorrow's weather forecast
 * data.get(1)
 * ```
 */
data class WeatherForecasts(
    val city: City,
    val updateTime: Long,
    private val forecast: List<DayWeather>
) : List<DayWeather> by forecast {
    /**
     * Returns today's weather.
     */
    val today: DayWeather get() = this[0]

    /**
     * Returns to all weather in the future.
     */
    val future: List<DayWeather> get() = this.subList(1, this.size)

    /**
     * Returns a human-readable time string.
     *
     * @param format The pattern applied to format the timestamp [WeatherForecasts.updateTime]
     * @param locale The locale whose date format symbols should be used
     * @see SimpleDateFormat
     */
    fun readableUpdateTime(
        format: String = "E HH:mm:ss",
        locale: Locale = Locale.ENGLISH /*FIXME Localization Locale.getDefault()*/
    ): String = formatTime(updateTime, pattern = format, locale = locale)
}
