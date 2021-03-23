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

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalPadding = compositionLocalOf { Padding(16.dp, 16.dp) }

fun Padding(horizontal: Dp, vertical: Dp): Padding = PaddingImpl(horizontal, vertical)

interface Padding {
    val horizontal: Dp
    val vertical: Dp
}

private class PaddingImpl(override val horizontal: Dp, override val vertical: Dp) : Padding
