package com.example.androiddevchallenge.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp


fun Modifier.rippleClickable(
  bounded: Boolean = true,
  radius: Dp = Dp.Unspecified,
  color: Color = Color.Unspecified,
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  onClick: () -> Unit
) = composed {
  clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = rememberRipple(bounded, radius, color),
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = onClick
  )
}