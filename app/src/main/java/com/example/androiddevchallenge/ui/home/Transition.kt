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

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 提供在不同 [HomeState] 下
 * 显示的 UI 所需的动画值
 *
 * 目前以硬编码形式将过渡总时长限制在 600 ms 内
 *
 * @author 凛 (https://github.com/RinOrz)
 */
data class HomeTransition(
    val toolBar: ToolBar,
    val titleBar: TitleBar,
    val pager: Pager,
    val forecastsBar: ForecastsBar,
) {

    class TitleBar(alpha: State<Float>) {
        val alpha by alpha
    }

    class ToolBar(alpha: State<Float>, offsetY: State<Dp>) {
        val alpha by alpha
        val offsetY by offsetY
    }

    class ForecastsBar(alpha: State<Float>, offsetY: State<Dp>) {
        val alpha by alpha
        val offsetY by offsetY
    }

    class Pager(
        currentBounce: State<Dp>,
        currentFade: State<Float>,
        otherClose: State<Float>,
        weatherOffset: State<Dp>,
        angle: State<Float>
    ) {
        val currentBounce by currentBounce
        val currentFade by currentFade
        val otherFade by otherClose
        val weatherOffset by weatherOffset
        val angle by angle
    }

    companion object {
        const val MaxDuration = 460L
    }
}

/**
 * 创建并记住 [HomeTransition],
 * 以根据不同的主页 [state] 来返回预期的动画值
 */
@Composable
fun rememberHomeTransition(state: HomeState): HomeTransition =
    updateTransition(state, label = "HomeTransition").run {
        val toolbar = rememberToolBar()
        val titleBar = rememberTitleBar()
        val pager = rememberPager()
        val forecastsBar = rememberForecastsBar()
        remember(this) {
            HomeTransition(toolbar, titleBar, pager, forecastsBar)
        }
    }

/** @see [ToolBar] */
@Composable
private fun HomeStateTransition.rememberToolBar(): HomeTransition.ToolBar {
    val alpha = animateFloat({ tween(400) }) { state ->
        if (state.isExpandDetails) 0f else 1f
    }
    val offsetY = animateDp({ tween(560) }) { state ->
        if (state.isExpandDetails) (-50).dp else 0.dp
    }
    return remember(this) { HomeTransition.ToolBar(alpha, offsetY) }
}

/** @see [TitleBar] */
@Composable
private fun HomeStateTransition.rememberTitleBar(): HomeTransition.TitleBar {
    val alpha = animateFloat({ tween() }) { state ->
        if (state.isExpandDetails) 0f else 1f
    }
    return remember(this) { HomeTransition.TitleBar(alpha) }
}

/** @see [ForecastsBar] */
@Composable
private fun HomeStateTransition.rememberForecastsBar(): HomeTransition.ForecastsBar {
    val alpha = animateFloat({ tween(500) }) { state ->
        if (state.isExpandDetails) 0f else 1f
    }
    val offsetY = animateDp({ tween(500) }) { state ->
        if (state.isExpandDetails) 80.dp else 0.dp
    }
    return remember(this) { HomeTransition.ForecastsBar(alpha, offsetY) }
}

/** @see [ForecastsBar] */
@Composable
private fun HomeStateTransition.rememberPager(): HomeTransition.Pager {
    val currentBounce = animateDp({ tween(420) }) { state ->
        if (state.isExpandDetails) 20.dp else 0.dp
    }
    val currentFade = animateFloat({ tween(400, delayMillis = 200) }) { state ->
        if (state.isExpandDetails) 0.2f else 1f
    }
    val weatherOffset = animateDp({ tween(400, delayMillis = 200) }) { state ->
        if (state.isExpandDetails) 50.dp else 0.dp
    }
    val otherClose = animateFloat({ tween(200) }) { state ->
        if (state.isExpandDetails) 0f else 1f
    }
    val angle = animateFloat({ tween(300) }) { state ->
        if (state.isExpandDetails) 0f else 5f
    }
    return remember(this) { HomeTransition.Pager(currentBounce, currentFade, otherClose, weatherOffset, angle) }
}

private typealias HomeStateTransition = Transition<HomeState>
