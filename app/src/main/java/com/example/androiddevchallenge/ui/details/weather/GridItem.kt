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
package com.example.androiddevchallenge.ui.details.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.ui.composable.Icon
import com.example.androiddevchallenge.ui.composable.LinearProgressIndicator
import com.example.androiddevchallenge.ui.theme.currentShapes

/**
 * Grid row in details.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun GridRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = LocalPadding.current.horizontal),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        content = content
    )
}

/**
 * Grid item in details.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.GridItem(
    icon: Int,
    title: String,
    subtitle: String,
    unit: String,
    progress: Float,
    maxProgress: Float,
    progressColors: List<Color>
) {
    val contentColor = LocalContentColor.current
    Row(
        modifier = Modifier.weight(1f).background(
            brush = Brush.verticalGradient(
                listOf(
                    contentColor.copy(alpha = 0.04f),
                    contentColor.copy(alpha = 0.01f)
                )
            ),
            shape = currentShapes.small
        ).aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = LocalPadding.current.vertical,
                    horizontal = LocalPadding.current.horizontal
                )
                .weight(1f)
        ) {
            Icon(id = icon, modifier = Modifier.size(36.dp))
            Text(
                text = subtitle,
                color = LocalContentColor.current.copy(alpha = 0.4f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 18.dp)
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                )
//        Text(
//          text = " $unit",
//          fontWeight = FontWeight.SemiBold,
//          fontSize = 18.sp,
//          modifier = Modifier.padding(bottom = 4.dp)
//        )
            }
        }

        LinearProgressIndicator(
            progress / maxProgress,
            brush = Brush.verticalGradient(progressColors),
            modifier = Modifier
                .padding(end = LocalPadding.current.horizontal)
                .padding(vertical = 34.dp)
                .fillMaxHeight()
                .width(6.dp)
                .clip(currentShapes.large)
        )
    }
}
