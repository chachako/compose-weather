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
package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.HourlyWeather
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.ui.composable.CoilImage
import com.example.androiddevchallenge.ui.composable.FlatButton
import com.example.androiddevchallenge.ui.composable.Icon
import com.example.androiddevchallenge.ui.composable.NumberText
import com.example.androiddevchallenge.ui.composable.animateAsState
import com.example.androiddevchallenge.ui.theme.currentShapes
import com.example.androiddevchallenge.ui.theme.currentTypography
import com.meowbase.toolkit.getNextHour
import com.meowbase.toolkit.isInTime
import com.meowbase.toolkit.toCalendar
import java.util.*
import kotlin.math.roundToInt

/**
 * Show the future hourly forecast.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun ForecastsBar(
    hourlyForecasts: List<HourlyWeather>,
    forecastDays: Int,
    highlightColors: List<Color>
) {
    val transition = rememberHomeTransition(state = LocalHomeState.current)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(transition.forecastsBar.alpha)
            .offset(y = transition.forecastsBar.offsetY)
    ) {
        Days(forecastDays)
        Times(hourlyForecasts, highlightColors)
    }
}

@Composable
private fun Days(forecastDays: Int) {
    Row(
        modifier = Modifier.padding(start = LocalPadding.current.horizontal, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Hourly Forecast", style = currentTypography.h6)
        Spacer(modifier = Modifier.weight(1f))
        FlatButton(
            onClick = { /*TODO*/ },
            colors = textButtonColors(contentColor = LocalContentColor.current.copy(alpha = 0.28f)),
            // 箭头图标自带稍微的边距，所以这里可以缩小右侧边距
            contentPadding = PaddingValues(start = 16.dp, end = 12.dp)
        ) {
            Text(
                text = "Next ${forecastDays - 1} Days",
                fontSize = 14.sp,
                style = currentTypography.caption
            )
            Icon(
                id = R.drawable.ic_baseline_navigate_next_24,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(18.dp)
            )
        }
    }
}

@Composable
private fun Times(
    hourlyForecasts: List<HourlyWeather>,
    highlightColors: List<Color>
) {
    val gradientStart by animateAsState(targetValue = highlightColors[0])
    val gradientEnd by animateAsState(targetValue = highlightColors[1])
    LazyRow(
        contentPadding = PaddingValues(
            start = 8.dp,
            end = LocalPadding.current.horizontal,
            top = LocalPadding.current.vertical,
            bottom = LocalPadding.current.vertical,
        )
    ) {
        items(hourlyForecasts) { weather ->
            val beginHour = Date(weather.timestamp)
            val endHour = Date(getNextHour(1, beginHour.toCalendar()))
            // 当前时间是否在预测时段内
            val isInPeriod = isInTime(beginHour, endHour)
            val background = if (isInPeriod) {
                Modifier.background(
                    brush = Brush.verticalGradient(listOf(gradientStart, gradientEnd)),
                    shape = currentShapes.large
                )
            } else {
                Modifier.background(
                    color = LocalContentColor.current.copy(0.04f),
                    shape = currentShapes.large
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .then(background)
                    .padding(16.dp)
            ) {
                if (isInPeriod) {
                    Text(
                        text = "Now",
                        fontWeight = FontWeight.SemiBold,
                        style = currentTypography.body2,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                } else {
                    NumberText(
                        text = "${weather.hour}:00",
                        style = currentTypography.body2,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                }
                CoilImage(
                    data = weather.type.icon,
                    fadeIn = true,
                    modifier = Modifier.size(54.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                NumberText(
                    text = "${weather.temperature.realtime.roundToInt()}°",
                    style = currentTypography.body1
                )
            }
        }
    }
}
