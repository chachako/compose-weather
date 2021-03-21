package com.example.androiddevchallenge.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.example.androiddevchallenge.ui.theme.currentShapes

@Composable
fun Circle(size: Dp, color: Color, modifier: Modifier = Modifier) {
  Canvas(modifier = modifier.size(size)) { drawCircle(color) }
}

@Composable
fun Shape(color: Color, modifier: Modifier = Modifier, shape: Shape = currentShapes.large) {
  Box(modifier = modifier.background(color, shape))
}