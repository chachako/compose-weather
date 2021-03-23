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

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.R

/**
 * The info of weather type.
 *
 * @author 凛 (https://github.com/RinOrz)
 *
 * @param icon The weather type icon
 * @param colors Theme gradient colors for different weather
 */
enum class WeatherType(
    @DrawableRes val icon: Int,
    val colors: List<Color>
) {
    Sunny(R.mipmap.sunny, listOf(Color(0xFFE15A5A), Color(0xFFC8A471))),
    Cloudy(R.mipmap.cloudy, listOf(Color(0xFF67A960), Color(0xFF8E7F45))),
    Shower(R.mipmap.shower, listOf(Color(0xFF945AF8), Color(0xFFDE616D))),
    Thundershower(R.mipmap.thundershower, listOf(Color(0xFF3E6FF0), Color(0xFF35C1B2))),
    Snow(R.mipmap.snow, listOf(Color(0xFF3AAAD9), Color(0xFFA394C5))),
    Fog(R.mipmap.fog, listOf(Color(0xFFEA9827), Color(0xFFB7B382))),
    Sandstorm(R.mipmap.sandstorm, listOf(Color(0xFFB57B45), Color(0xFF9A8787))),
    Haze(R.mipmap.haze, listOf(Color(0xFF5B687A), Color(0xFFB6BBC3))),
}
