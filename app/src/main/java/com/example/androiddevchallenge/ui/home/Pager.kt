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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.DayWeather
import com.example.androiddevchallenge.data.Temperature
import com.example.androiddevchallenge.data.WeatherForecasts
import com.example.androiddevchallenge.ui.composable.CoilImage
import com.example.androiddevchallenge.ui.composable.Image
import com.example.androiddevchallenge.ui.composable.RoundedTrapezoid
import com.example.androiddevchallenge.ui.composable.clickable
import com.example.androiddevchallenge.ui.theme.currentTypography
import com.example.androiddevchallenge.util.lerp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.pageChangedFlow
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * The part of displaying weather summary information.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun Pager(
    modifier: Modifier,
    currentCityPage: Int,
    onPageClick: () -> Unit,
    onPageChanged: (Int) -> Unit,
    allCityForecasts: List<WeatherForecasts>,
) {
    val homeState = LocalHomeState.current
    val pagerState = rememberPagerState(
        pageCount = allCityForecasts.size,
        initialPage = currentCityPage
    )
    val transition = rememberHomeTransition(homeState)
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .graphicsLayer(clip = false),
            offscreenLimit = 2
        ) { page ->
            val pageScrollOffset = (scrollPosition - page).absoluteValue
            val fraction = 1f - pageScrollOffset.coerceIn(0f, 1f)
            Item(
                weather = allCityForecasts[page].today,
                fade = when {
                    homeState.isExpandDetails -> {
                        if (page == currentPage) transition.pager.currentFade else 0.2f
                    }
                    else -> lerp(0.2f, 1f, fraction)
                },
                scale = lerp(0.9f, 1f, fraction),
                // The weather illustration shifts in the opposite direction when sliding
                parallax = when {
                    homeState.isExpandDetails -> {
                        if (page == currentPage) transition.pager.weatherOffset else 0.dp
                    }
                    else -> if (page == currentPage) {
                        lerp(45.dp, 0.dp, fraction)
                    } else {
                        lerp((-45).dp, 0.dp, fraction)
                    }
                },
                offsetY = if (homeState.isExpandDetails && page == currentPage) {
                    transition.pager.currentBounce
                } else {
                    0.dp
                },
                onPageClick = onPageClick
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPagerIndicator(
            pagerState,
            width = 13.dp,
            height = 4.dp,
            modifier = Modifier.alpha(transition.pager.otherFade)
        )
    }
    LaunchedEffect(pagerState) {
        pagerState.pageChangedFlow.collect { onPageChanged(it) }
    }
}

@Composable
private fun Item(
    fade: Float,
    scale: Float,
    parallax: Dp,
    offsetY: Dp,
    onPageClick: () -> Unit,
    weather: DayWeather
) {
    val weatherType = weather.type
    val transition = rememberHomeTransition(LocalHomeState.current)

    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .graphicsLayer(
                clip = false,
                scaleX = scale,
                scaleY = scale,
                alpha = fade
            )
            .offset(y = offsetY)
            .clickable(indication = null, onClick = onPageClick),
        contentAlignment = Alignment.BottomCenter
    ) {
        val illustrationHeight = remember(maxHeight) { maxHeight * 0.68f }

        RoundedTrapezoid(
            angle = transition.pager.angle,
            cornerRadius = maxWidth * 0.22f,
            brush = Brush.verticalGradient(weatherType.colors),
            // 高度小于天气图
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight - (illustrationHeight / 3f))
                .align(Alignment.TopCenter),
        )
        Texts(
            weather = weatherType.name,
            temperature = weather.temperature.realtime,
            illustrationHeight = illustrationHeight,
        )
        CoilImage(
            data = weatherType.icon,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .offset(x = parallax)
                .height(illustrationHeight)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun Texts(
    weather: String,
    temperature: Double,
    illustrationHeight: Dp
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = weather,
            style = currentTypography.caption,
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally),
        )
        Box(modifier = Modifier.weight(1f)) {
            TemperatureText(
                temperature = temperature,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        // 因为插图实际上是比高度小一些的，所以我们在这里做一个空间以适用 weight
        Spacer(modifier = Modifier.size(illustrationHeight / 1.5f))
    }
}

@Composable
private fun TemperatureText(temperature: Double, modifier: Modifier = Modifier) {
    val numberText = temperature.roundToInt().absoluteValue.toString()
    val isTens = numberText.length > 1

    ConstraintLayout(modifier) {
        val (negative, number, unit) = createRefs()

        // 负数
        if (temperature < 0) {
            Image(
                id = R.drawable.ic_temp_negative,
                alpha = 0.6f,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(18.dp)
                    .offset(x = if (isTens) 8.dp else 16.dp)
                    .constrainAs(negative) {
                        start.linkTo(parent.start)
                        end.linkTo(number.start)
                        top.linkTo(number.top)
                        bottom.linkTo(number.bottom)
                    },
            )
        }

        Row(modifier = Modifier.constrainAs(number) { centerHorizontallyTo(parent) }) {
            numberText.forEach { char ->
                Temperature.getNumberIcon(char)?.let {
                    Image(
                        id = it,
                        modifier = Modifier.fillMaxHeight(),
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }

        Image(
            id = R.drawable.ic_temp_unit,
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
                .offset(x = if (isTens) (-16).dp else (-22).dp, y = (-26).dp)
                .constrainAs(unit) {
                    end.linkTo(parent.end)
                    start.linkTo(number.end)
                    top.linkTo(number.top)
                    bottom.linkTo(number.bottom)
                },
            alpha = 0.6f
        )
    }
}
