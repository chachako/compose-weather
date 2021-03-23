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

import androidx.compose.foundation.Canvas
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.isSpecified
import com.meowbase.toolkit.toRadians
import kotlin.math.tan

/**
 * 圆角梯形
 * TODO: Move to separate library module.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun RoundedTrapezoid(
    angle: Float,
    brush: Brush,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = Dp.Unspecified,
    alpha: Float = 1f,
    using: TrapezoidShape.Corner = TrapezoidShape.Corner.BottomEnd,
) { GenericRoundedTrapezoid(angle, brush, Color.Unspecified, modifier, cornerRadius, alpha, using) }

/**
 * 圆角梯形
 * TODO: Move to separate library module.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun RoundedTrapezoid(
    angle: Float,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = Dp.Unspecified,
    color: Color = LocalContentColor.current,
    alpha: Float = 1f,
    using: TrapezoidShape.Corner = TrapezoidShape.Corner.BottomEnd,
) { GenericRoundedTrapezoid(angle, null, color, modifier, cornerRadius, alpha, using) }

/**
 * 圆角梯形
 * TODO: Move to separate library module.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
private fun GenericRoundedTrapezoid(
    angle: Float,
    brush: Brush?,
    color: Color,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = Dp.Unspecified,
    alpha: Float = 1f,
    using: TrapezoidShape.Corner = TrapezoidShape.Corner.BottomEnd,
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val shape = remember(angle, using) { TrapezoidShape(angle, using) }
    val radius = remember(cornerRadius) { with(density) { cornerRadius.toPx() } }
    Canvas(modifier) {
        val outline = shape.createOutline(size, layoutDirection, density) as Outline.Generic
        val paint = Paint().apply {
            if (color.isSpecified) this.color = color
            if (cornerRadius.isSpecified) this.pathEffect = PathEffect.cornerPathEffect(radius)
        }
        brush?.applyTo(size, paint, alpha)
        drawContext.canvas.drawPath(outline.path, paint)
    }
}

/**
 * 直角梯形
 * TODO: Move to separate library module.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
data class TrapezoidShape internal constructor(
    val angle: Float,
    val using: Corner = Corner.BottomEnd
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val anotherHeight = size.width * tan(angle.toRadians())
        val path = Path().apply {
            when (using) {
                Corner.TopStart -> TODO()
                Corner.TopEnd -> TODO()
                Corner.BottomEnd -> {
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height - anotherHeight)
                    lineTo(0f, size.height)
                }
                Corner.BottomStart -> TODO()
            }
            close()
        }
        return Outline.Generic(path)
    }

    enum class Corner {
        TopStart,
        TopEnd,
        BottomEnd,
        BottomStart,
    }
}
