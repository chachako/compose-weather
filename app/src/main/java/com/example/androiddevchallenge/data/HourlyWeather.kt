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

/**
 * The weather data for an entire hour.
 *
 * @author 凛 (https://github.com/RinOrz)
 *
 * @param timestamp Represents the weather data belongs to this hour.
 */
data class HourlyWeather(
    override val temperature: Temperature,
    override val type: WeatherType,
    override val aqi: AQI,
    val timestamp: Long,
) : Weather {
    /** Hour number to which weather belongs. */
    val hour: Int get() = formatTime(timestamp, pattern = "HH").toInt()
}
