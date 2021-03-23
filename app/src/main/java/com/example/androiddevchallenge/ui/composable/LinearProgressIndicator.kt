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
package com.example.androiddevchallenge.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LinearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth
private val LinearIndicatorWidth = 240.dp

@Composable
fun LinearProgressIndicator(
    progress: Float,
    brush: Brush,
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalContentColor.current.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    BoxWithConstraints(
        modifier
            .background(backgroundColor)
            .progressSemantics(progress)
            .focusable(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val barStart = maxHeight * (1f - progress)
        Box(modifier = Modifier.height(barStart).fillMaxWidth().background(brush))
    }
}
