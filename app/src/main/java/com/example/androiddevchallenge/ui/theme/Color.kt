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

@file:Suppress("unused")

package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * All theme colors of compose-weather application.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
val Colors.blue get() = Color(0xFF4452FB)


/**
 * All gradient colors of compose-weather application.
 */
object GradientColors {
  val textStart
    @Composable
    get() = if (MaterialTheme.colors.isLight) {
      Color(0xFF34374D)
    } else {
      Color.White
    }

  val textEnd
    @Composable
    get() = textStart.copy(alpha = 0.04f)
}

typealias GradientColor = Pair<Color, Color>