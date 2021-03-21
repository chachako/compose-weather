package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.ui.CoilImage
import com.example.androiddevchallenge.ui.FlatButton
import com.example.androiddevchallenge.ui.Icon
import com.example.androiddevchallenge.ui.NumberText
import com.example.androiddevchallenge.ui.theme.GradientColor
import com.example.androiddevchallenge.ui.theme.currentShapes
import com.example.androiddevchallenge.ui.theme.currentTypography
import com.meowbase.toolkit.formatTime
import com.meowbase.toolkit.getNextHour
import com.meowbase.toolkit.isInTime
import com.meowbase.toolkit.toCalendar
import java.util.*
import kotlin.math.roundToInt

/**
 * Weather selection for different time periods.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun Period(hourlyForecast: List<Weather.Info>, forecastDays: Int, highlightColors: GradientColor) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Days(forecastDays)
    Times(hourlyForecast, highlightColors)
  }
}

@Composable
private fun Days(forecastDays: Int) {
  Row(
    modifier = Modifier.padding(start = LocalPadding.current.horizontal, end = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = "Hourly Forecast", style = currentTypography.h6)
    Spacer(modifier = Modifier.weight(1f))
    FlatButton(
      onClick = { /*TODO*/ },
      colors = textButtonColors(contentColor = LocalContentColor.current.copy(alpha = 0.28f)),
      // 箭头图标自带稍微的边距，所以这里可以缩小右侧边距
      contentPadding = PaddingValues(start = 16.dp, end = 12.dp)
    ) {
      Text(
        text = "Next ${forecastDays - 1} Days",
        fontSize = 14.sp,
        style = currentTypography.caption
      )
      Icon(
        id = R.drawable.ic_baseline_navigate_next_24,
        modifier = Modifier
          .padding(start = 4.dp)
          .size(18.dp)
      )
    }
  }
}

@Composable
private fun Times(hours: List<Weather.Info>, highlightColors: GradientColor) {
  val gradient = highlightColors.toList()
  LazyRow(
    contentPadding = PaddingValues(
      start = 8.dp,
      end = LocalPadding.current.horizontal,
      top = LocalPadding.current.vertical,
      bottom = LocalPadding.current.vertical,
    )
  ) {
    items(hours) {
      val beginHour = Date(it.forecastTime!!)
      val endHour = Date(getNextHour(1, beginHour.toCalendar()))
      val isInPeriod = isInTime(beginHour, endHour)
      val background = if (isInPeriod) {
        Modifier.background(
          brush = Brush.verticalGradient(gradient),
          shape = currentShapes.large
        )
      } else {
        Modifier.background(
          color = LocalContentColor.current.copy(0.04f),
          shape = currentShapes.large
        )
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .padding(start = 16.dp)
          .then(background)
          .padding(16.dp)
      ) {
        NumberText(
          text = formatTime(beginHour.time, "HH:mm"),
          style = currentTypography.body2,
          modifier = Modifier.padding(top = 6.dp),
        )
        CoilImage(
          data = it.kind.icon,
          modifier = Modifier.size(54.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        NumberText(
          text = "${it.temperature.realtime.roundToInt()}°",
          style = currentTypography.body1
        )
      }
    }
  }
}
