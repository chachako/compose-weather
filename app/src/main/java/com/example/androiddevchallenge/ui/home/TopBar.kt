package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.ui.*
import com.example.androiddevchallenge.ui.theme.currentColors
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
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(
        start = 14.dp,
        top = 12.dp,
        bottom = 16.dp,
        end = LocalPadding.current.horizontal,
      ),
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
  TitleBar(title, subtitle, trailingText, onTrailingButtonClick, isPositioned)
}

@Composable
private fun TitleBar(
  title: String,
  subtitle: String,
  trailingText: String,
  onTrailingButtonClick: () -> Unit,
  isPositioned: Boolean
) {
  val color = LocalContentColor.current
  Row(
    modifier = Modifier.padding(horizontal = LocalPadding.current.horizontal),
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