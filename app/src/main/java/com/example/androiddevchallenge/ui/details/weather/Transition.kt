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
 * 提供在不同 [DetailsState] 下
 * 显示的 UI 所需的动画值
 *
 * 目前以硬编码形式将过渡总时长限制在 600 ms 内
 *
 * @author 凛 (https://github.com/RinOrz)
 */
data class DetailsTransition(
    val board: Board,
) {

    class Board(alpha: State<Float>, offsetY: State<Dp>) {
        val alpha by alpha
        val offsetY by offsetY
    }

    companion object {
        const val MaxDuration = 500L
    }
}

/**
 * 创建并记住 [DetailsState],
 * 以根据不同的主页 [state] 来返回预期的动画值
 */
@Composable
fun rememberDetailsTransition(state: DetailsState): DetailsTransition =
    updateTransition(state, label = "HomeTransition").run {
        val toolbar = rememberBoard()
        remember(this) {
            DetailsTransition(toolbar)
        }
    }

/** @see [Board] */
@Composable
private fun DetailsStateTransition.rememberBoard(): DetailsTransition.Board {
    val alpha = animateFloat({ tween(400, delayMillis = 20) }) { state ->
        if (state.isExpand) 1f else 0f
    }
    val offsetY = animateDp({ tween(500, delayMillis = 20) }) { state ->
        if (state.isExpand) 0.dp else 100.dp
    }
    return remember(this) { DetailsTransition.Board(alpha, offsetY) }
}

private typealias DetailsStateTransition = Transition<DetailsState>
