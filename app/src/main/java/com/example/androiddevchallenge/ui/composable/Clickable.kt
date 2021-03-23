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

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.Flow

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

fun Modifier.clickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    interactionSource: MutableInteractionSource = DefaultMutableInteractionSource,
    indication: Indication? = DefaultIndication,
    onClick: () -> Unit
) = composed {
    val realIndication = if (indication === DefaultIndication) {
        LocalIndication.current
    } else {
        indication
    }
    val realInteractionSource = if (interactionSource === DefaultMutableInteractionSource) {
        remember { MutableInteractionSource() }
    } else {
        interactionSource
    }

    clickable(realInteractionSource, realIndication, enabled, onClickLabel, role, onClick)
}

private object DefaultIndication : Indication {
    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        TODO("Not yet implemented")
    }
}

private object DefaultMutableInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction>
        get() = TODO("Not yet implemented")

    override suspend fun emit(interaction: Interaction) {
        TODO("Not yet implemented")
    }

    override fun tryEmit(interaction: Interaction): Boolean {
        TODO("Not yet implemented")
    }
}
