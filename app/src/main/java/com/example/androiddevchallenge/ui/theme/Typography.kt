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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R

val typography = Typography(
    defaultFontFamily = WeatherFont.Nunito,
    h1 = TextStyle(
        fontFamily = WeatherFont.JetBrainsMono,
        fontWeight = FontWeight.Bold,
        fontSize = 160.sp,
    ),
    h2 = TextStyle(
        fontFamily = WeatherFont.JetBrainsMono,
        fontWeight = FontWeight.Bold,
        fontSize = 110.sp,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 1.11.sp,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = (-0.1).sp,
    ),
    subtitle1 = TextStyle(
        fontSize = 15.sp,
        letterSpacing = 0.2.sp,
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = (-0.2).sp,
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 13.sp,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        letterSpacing = 1.25.sp,
    ),
)

object WeatherFont {
    val JetBrainsMono = FontFamily(
        Font(R.font.jetbrains_mono_bold, FontWeight.Bold)
    )

    val Rubik = FontFamily(
        Font(R.font.rubik_regular),
        Font(R.font.rubik_light, FontWeight.Light),
    )

    val Nunito = FontFamily(
        Font(R.font.nunito_regular),
        Font(R.font.nunito_bold, FontWeight.Bold),
        Font(R.font.nunito_semibold, FontWeight.SemiBold),
        Font(R.font.nunito_extrabold, FontWeight.ExtraBold),
    )
}
