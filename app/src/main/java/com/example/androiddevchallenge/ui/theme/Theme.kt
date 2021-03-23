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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    background = Color(0xFF111111),
    onBackground = Color.White,
    surface = Color.White.copy(0.025f),
    onSurface = Color.White,
)

@Composable
fun WeatherTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // TODO Light Theme
    val colors = DarkColorPalette
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

/**
 * The custom theme data of compose-weather application.
 *
 * @author 凛 (https://github.com/RinOrz)
 */

val currentTypography
    @Composable
    get() = MaterialTheme.typography

val currentShapes
    @Composable
    get() = MaterialTheme.shapes

val currentColors
    @Composable
    get() = MaterialTheme.colors

val currentGradientColors = GradientColors
