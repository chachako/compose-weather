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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.animateRectAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : Any> animateAsState(
    targetValue: T,
    durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing,
    finishedListener: ((T) -> Unit)? = null
): State<T> = when (targetValue) {
    is Dp -> animateDpAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (Dp) -> Unit)
    is Float -> animateFloatAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener = finishedListener as? (Float) -> Unit)
    is Color -> animateColorAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (Color) -> Unit)
    is Int -> animateIntAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (Int) -> Unit)
    is Offset -> animateOffsetAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (Offset) -> Unit)
    is IntOffset -> animateIntOffsetAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (IntOffset) -> Unit)
    is IntSize -> animateIntSizeAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (IntSize) -> Unit)
    is Rect -> animateRectAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (Rect) -> Unit)
    is Size -> animateSizeAsState(targetValue, tween(durationMillis, delayMillis, easing), finishedListener as? (Size) -> Unit)
    else -> TODO("Support to ${targetValue::class.java.name}")
} as State<T>

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : Any> animateAsState(
    targetValue: T,
    visibilityThreshold: T,
    dampingRatio: Float = Spring.DampingRatioNoBouncy,
    stiffness: Float = Spring.StiffnessMedium,
    finishedListener: ((T) -> Unit)? = null
): State<T> = when (targetValue) {
    is Dp -> animateDpAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Dp>, finishedListener as? (Dp) -> Unit)
    is Float -> animateFloatAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Float>, finishedListener = finishedListener as? (Float) -> Unit)
    is Color -> animateColorAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Color>, finishedListener as? (Color) -> Unit)
    is Int -> animateIntAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Int>, finishedListener as? (Int) -> Unit)
    is Offset -> animateOffsetAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Offset>, finishedListener as? (Offset) -> Unit)
    is IntOffset -> animateIntOffsetAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<IntOffset>, finishedListener as? (IntOffset) -> Unit)
    is IntSize -> animateIntSizeAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<IntSize>, finishedListener as? (IntSize) -> Unit)
    is Rect -> animateRectAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Rect>, finishedListener as? (Rect) -> Unit)
    is Size -> animateSizeAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold) as AnimationSpec<Size>, finishedListener as? (Size) -> Unit)
    else -> TODO("Support to ${targetValue::class.java.name}")
} as State<T>

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : Any> animateAsState(
    targetValue: T,
    dampingRatio: Float = Spring.DampingRatioNoBouncy,
    stiffness: Float = Spring.StiffnessMedium,
    finishedListener: ((T) -> Unit)? = null
): State<T> = when (targetValue) {
    is Dp -> animateDpAsState(targetValue, spring(dampingRatio, stiffness, Dp.VisibilityThreshold), finishedListener as? (Dp) -> Unit)
    is Float -> animateFloatAsState(targetValue, spring(dampingRatio, stiffness, visibilityThreshold = 0.01f), finishedListener = finishedListener as? (Float) -> Unit)
    is Color -> animateColorAsState(targetValue, spring(dampingRatio, stiffness, null), finishedListener as? (Color) -> Unit)
    is Int -> animateIntAsState(targetValue, spring(dampingRatio, stiffness, Int.VisibilityThreshold), finishedListener as? (Int) -> Unit)
    is Offset -> animateOffsetAsState(targetValue, spring(dampingRatio, stiffness, Offset.VisibilityThreshold), finishedListener as? (Offset) -> Unit)
    is IntOffset -> animateIntOffsetAsState(targetValue, spring(dampingRatio, stiffness, IntOffset.VisibilityThreshold), finishedListener as? (IntOffset) -> Unit)
    is IntSize -> animateIntSizeAsState(targetValue, spring(dampingRatio, stiffness, IntSize.VisibilityThreshold), finishedListener as? (IntSize) -> Unit)
    is Rect -> animateRectAsState(targetValue, spring(dampingRatio, stiffness, Rect.VisibilityThreshold), finishedListener as? (Rect) -> Unit)
    is Size -> animateSizeAsState(targetValue, spring(dampingRatio, stiffness, Size.VisibilityThreshold), finishedListener as? (Size) -> Unit)
    else -> TODO("Support to ${targetValue::class.java.name}")
} as State<T>
