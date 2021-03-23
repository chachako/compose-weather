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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.ui.composable.CoilImage
import com.example.androiddevchallenge.ui.composable.FlatButton
import com.example.androiddevchallenge.ui.composable.Icon
import com.example.androiddevchallenge.ui.composable.NumberText
import com.example.androiddevchallenge.ui.theme.currentTypography

/**
 * The separate TopBar of HomePage.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun TopBar(
    title: String,
    subtitle: String,
    avatar: Any,
    trailingText: String,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTrailingButtonClick: () -> Unit,
    isPositioned: Boolean,
) {
    ToolBar(avatar, onMenuClick, onSearchClick)
    TitleBar(title, subtitle, trailingText, onTrailingButtonClick, isPositioned)
}

@Composable
private fun ToolBar(
    avatar: Any,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    val transition = rememberHomeTransition(state = LocalHomeState.current)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 14.dp,
                top = 12.dp,
                bottom = 16.dp,
                end = LocalPadding.current.horizontal,
            )
            .alpha(transition.toolBar.alpha)
            .offset(y = transition.toolBar.offsetY),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                id = R.drawable.ic_menu,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onSearchClick) {
            Icon(
                id = R.drawable.ic_search,
                modifier = Modifier.size(20.dp)
            )
        }
        CoilImage(
            data = avatar,
            modifier = Modifier
                .padding(start = 14.dp)
                .size(38.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun TitleBar(
    title: String,
    subtitle: String,
    trailingText: String,
    onTrailingButtonClick: () -> Unit,
    isPositioned: Boolean
) {
    val transition = rememberHomeTransition(state = LocalHomeState.current)
    val color = LocalContentColor.current
    Row(
        modifier = Modifier
            .padding(horizontal = LocalPadding.current.horizontal)
            .alpha(transition.titleBar.alpha),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = color,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = currentTypography.h5,
                )
                if (isPositioned) CoilImage(
                    data = R.mipmap.ic_location,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(18.dp)
                )
            }
            Text(
                text = subtitle,
                maxLines = 1,
                color = color.copy(0.18f),
                overflow = TextOverflow.Ellipsis,
                style = currentTypography.subtitle2
            )
        }
        Spacer(modifier = Modifier.width(LocalPadding.current.horizontal))
        FlatButton(
            onClick = onTrailingButtonClick,
            colors = buttonColors(
                backgroundColor = color.copy(0.04f),
                contentColor = color
            ),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            )
        ) {
            Icon(id = R.drawable.ic_aqi, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            NumberText(text = trailingText)
        }
    }
}
