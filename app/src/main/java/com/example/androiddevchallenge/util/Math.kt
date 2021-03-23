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
package com.example.androiddevchallenge.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
fun <T : Any> lerp(
    start: T,
    stop: T,
    fraction: Float,
): T = when {
    start is Dp && stop is Dp -> lerp(start, stop, fraction)
    start is Float && stop is Float -> lerp(start, stop, fraction)
    start is Int && stop is Int -> lerp(start, stop, fraction)
    start is Long && stop is Long -> lerp(start, stop, fraction)
    start is Color && stop is Color -> lerp(start, stop, fraction)
    start is IntRect && stop is IntRect -> lerp(start, stop, fraction)
    start is RoundRect && stop is RoundRect -> lerp(start, stop, fraction)
    start is Rect && stop is Rect -> lerp(start, stop, fraction)
    start is Offset && stop is Offset -> lerp(start, stop, fraction)
    start is Size && stop is Size -> lerp(start, stop, fraction)
    else -> TODO("Support to ${start::class.java.name} and ${stop::class.java.name}")
} as T
