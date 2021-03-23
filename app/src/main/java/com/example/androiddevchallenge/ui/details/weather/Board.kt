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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.data.Temperature
import com.example.androiddevchallenge.ui.composable.CoilImage
import com.example.androiddevchallenge.ui.composable.FlatButton
import com.example.androiddevchallenge.ui.composable.Icon
import com.example.androiddevchallenge.ui.composable.Image
import com.example.androiddevchallenge.ui.composable.NumberText
import com.example.androiddevchallenge.ui.composable.RoundedTrapezoid
import com.example.androiddevchallenge.ui.theme.currentTypography
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * Used to display important info about the weather.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun Board(
    temperature: Double,
    title: String,
    subtitle: String,
    trailingText: String,
    onTrailingButtonClick: () -> Unit,
    colors: List<Color>,
    icon: Int,
) = with(LocalDensity.current) {
    var boardHeight by remember { mutableStateOf(Dp.Hairline) }
    var trailingSize by remember { mutableStateOf(Dp.Hairline) }
    val transition = rememberDetailsTransition(LocalWeatherDetailsState.current)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalPadding.current.horizontal)
            .padding(top = LocalPadding.current.vertical)
            .offset(y = transition.board.offsetY)
            .alpha(transition.board.alpha)
            .graphicsLayer(clip = false)
    ) {
        Background(
            modifier = Modifier.height(boardHeight),
            colors
        )
        TrailingButton(
            modifier = Modifier
                .onSizeChanged { trailingSize = it.width.toDp() }
                .align(Alignment.TopEnd)
                .padding(28.dp),
            trailingText,
            onTrailingButtonClick
        )
        Column(
            modifier = Modifier
                .onSizeChanged { boardHeight = it.height.toDp() }
                .padding(
                    horizontal = LocalPadding.current.horizontal,
                    vertical = LocalPadding.current.vertical
                )
        ) {
            // 避免与 AQI 按钮重叠
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                        end = trailingSize
                    )
            ) {
                Text(
                    text = subtitle,
                    maxLines = 1,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = LocalContentColor.current.copy(0.24f),
                    style = currentTypography.h5,
                )
                Text(
                    text = title,
                    maxLines = 1,
                    fontSize = 25.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = LocalContentColor.current,
                    style = currentTypography.h5,
                )
            }
            TemperatureText(
                temperature,
                modifier = Modifier.padding(
                    start = if (temperature < 0) Dp.Hairline else 4.dp,
                    top = 8.dp,
                )
            )
        }
        CoilImage(
            icon,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 80.dp)
                .height(238.dp)
                .offset(24.dp)
        )
    }
}

@Composable
private fun TrailingButton(
    modifier: Modifier,
    trailingText: String,
    onTrailingButtonClick: () -> Unit,
) {
    val color = LocalContentColor.current
    FlatButton(
        modifier = modifier,
        onClick = onTrailingButtonClick,
        colors = buttonColors(
            backgroundColor = color.copy(0.08f),
            contentColor = color
        ),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        )
    ) {
        Icon(id = R.drawable.ic_aqi, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        NumberText(text = trailingText)
    }
}

@Composable
private fun TemperatureText(temperature: Double, modifier: Modifier = Modifier) {
    val numberText = temperature.roundToInt().absoluteValue.toString()

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        // 负数
        if (temperature < 0) {
            Image(
                id = R.drawable.ic_temp_negative,
                modifier = Modifier.width(36.dp),
                alpha = 0.6f
            )
        }
        numberText.forEach { char ->
            Temperature.getNumberIcon(char)?.let {
                Image(
                    id = it,
                    modifier = Modifier.height(116.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        Image(
            id = R.drawable.ic_temp_unit,
            modifier = Modifier
                .width(28.dp)
                .offset(y = (-26).dp),
            alpha = 0.6f
        )
    }
}

@Composable
private fun Background(
    modifier: Modifier,
    colors: List<Color>
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        RoundedTrapezoid(
            angle = 0f,
            cornerRadius = maxWidth * 0.16f,
            brush = Brush.verticalGradient(colors),
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight),
        )
    }
}
