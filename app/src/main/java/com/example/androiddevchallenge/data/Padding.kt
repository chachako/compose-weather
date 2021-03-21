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