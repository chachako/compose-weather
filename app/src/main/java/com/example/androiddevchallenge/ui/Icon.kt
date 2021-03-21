package com.example.androiddevchallenge.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun Icon(
  @DrawableRes id: Int,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
  androidx.compose.material.Icon(
    painter = painterResource(id),
    contentDescription, modifier, tint
  )
}