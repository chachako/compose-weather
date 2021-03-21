package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect.Companion.cornerPathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.ui.CoilImage
import com.example.androiddevchallenge.ui.HorizontalPager
import com.example.androiddevchallenge.ui.Image
import com.example.androiddevchallenge.ui.Shape
import com.example.androiddevchallenge.ui.rememberPagerState
import com.example.androiddevchallenge.ui.theme.GradientColor
import com.example.androiddevchallenge.ui.theme.currentTypography
import kotlin.math.roundToInt


/**
 * The part of displaying weather summary information.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun Summary(
  modifier: Modifier,
  weatherIndex: Int,
  allCityWeathers: List<Weather>,
  onPageChanged: (Int) -> Unit
) {
  val state = rememberPagerState(
    pageCount = allCityWeathers.size,
    initialPage = weatherIndex
  )
  val fraction = state.currentPageOffset
  if (fraction == 0f) {
    if (state.currentPage != weatherIndex) {
      onPageChanged(state.currentPage)
    }
  }
  Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    HorizontalPager(
      state = rememberPagerState(
        pageCount = allCityWeathers.size,
        initialPage = weatherIndex
      ),
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      offscreenLimit = 2
    ) {
      Item(
        weather = allCityWeathers[it],
        fade = if (it == currentPage) {
          lerp(1f, 0.2f, fraction)
        } else {
          lerp(0.2f, 1f, fraction)
        },
        // The weather illustration shifts in the opposite direction when sliding
        parallax = if (it == currentPage) {
          lerp(0f, 100f, fraction)
        } else {
          lerp(-100f, 0f, fraction)
        },
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    PageIndicator(state.currentPage, state.pageCount)
  }
}

@Composable
private fun Item(weather: Weather, fade: Float, parallax: Float) {
  val info = weather.today
  val kind = info.kind
  var illustrationHeight by remember { mutableStateOf(0) }

  Box(
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .fillMaxWidth(0.83f)
      .alpha(fade)
      .drawBehind {
        drawBoard(
          width = size.width,
          // 高度必须小于天气图
          height = size.height - (illustrationHeight / 3f),
          colors = kind.colors,
        )
      },
    contentAlignment = Alignment.BottomCenter
  ) {
    Text(
      weather = kind.name,
      temperature = info.temperature.realtime,
      illustrationHeight = illustrationHeight,
    )
    Illustration(
      modifier = Modifier
        .offset(x = parallax.dp)
        .onSizeChanged { illustrationHeight = it.height },
      resId = kind.icon
    )
  }
}

@Composable
private fun Illustration(
  modifier: Modifier,
  resId: Int
) {
  CoilImage(
    data = resId,
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .fillMaxWidth()
      .then(modifier),
    contentScale = ContentScale.Fit
  )
}

@Composable
private fun Text(
  weather: String,
  temperature: Double,
  illustrationHeight: Int
) {
  Column(modifier = Modifier.fillMaxSize()) {
    Text(
      text = weather,
      style = currentTypography.caption,
      modifier = Modifier
        .padding(top = 32.dp)
        .align(Alignment.CenterHorizontally),
    )
    Box(modifier = Modifier.weight(1f)) {
      Temperature(
        temperature = temperature,
        modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.Center)
      )
    }
    // 因为插图实际上是比高度小一些的，所以我们在这里做一个空间来避免显示不正确
    val fakeHeight = with(LocalDensity.current) { (illustrationHeight / 1.46f).toDp() }
    Spacer(modifier = Modifier.size(fakeHeight))
  }
}

@Composable
private fun Temperature(temperature: Double, modifier: Modifier) {
  val numberText = temperature.roundToInt().toString()
  val isTens = numberText.replace("-", "").length > 1

  fun getNumberIcon(char: Char) = when (char) {
    '0' -> R.drawable.ic_temp_0
    '1' -> R.drawable.ic_temp_1
    '2' -> R.drawable.ic_temp_2
    '3' -> R.drawable.ic_temp_3
    '4' -> R.drawable.ic_temp_4
    '5' -> R.drawable.ic_temp_5
    '6' -> R.drawable.ic_temp_6
    '7' -> R.drawable.ic_temp_7
    '8' -> R.drawable.ic_temp_8
    '9' -> R.drawable.ic_temp_9
    else -> null
  }

  ConstraintLayout(modifier) {
    val (negative, number, unit) = createRefs()

    // 负数
    if (temperature < 0) {
      Image(
        id = R.drawable.ic_temp_negative,
        modifier = Modifier
          .width(72.dp)
          .offset(x = if (isTens) 8.dp else 16.dp)
          .constrainAs(negative) {
            start.linkTo(parent.start)
            end.linkTo(number.start)
            top.linkTo(number.top)
            bottom.linkTo(number.bottom)
          },
        alpha = 0.8f
      )
    }

    Row(modifier = Modifier.constrainAs(number) { centerHorizontallyTo(parent) }) {
      numberText.forEach { char ->
        getNumberIcon(char)?.let {
          Image(
            id = it,
            modifier = Modifier.height(196.dp),
            contentScale = ContentScale.FillHeight
          )
        }
      }
    }

    Image(
      id = R.drawable.ic_temp_unit,
      modifier = Modifier
        .width(60.dp)
        .offset(x = if (isTens) (-16).dp else (-22).dp, y = (-26).dp)
        .constrainAs(unit) {
          end.linkTo(parent.end)
          start.linkTo(number.end)
          top.linkTo(number.top)
          bottom.linkTo(number.bottom)
        },
      alpha = 0.8f
    )
  }
}

@Composable
private fun PageIndicator(selected: Int, count: Int) {
  require(count > 0)
  val currentColor = LocalContentColor.current
  val disabledAlpha = ContentAlpha.disabled
  Row(
    modifier = Modifier
      .wrapContentSize()
      .padding(top = 2.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    repeat(count) {
      val color = currentColor.copy(alpha = if (it == selected) 1f else disabledAlpha)
      Shape(
        color, modifier = Modifier
          .height(4.dp)
          .width(13.dp)
      )
      if (it < count) {
        Spacer(modifier = Modifier.width(6.dp))
      }
    }
  }
}

private fun DrawScope.drawBoard(width: Float, height: Float, colors: GradientColor) {
  val radius = width * 0.2f
  val path = Path().apply {
    lineTo(width, 0f)
    lineTo(width, height * 0.9f)
    lineTo(0f, height)
    close()
  }
  val paint = Paint().apply {
    shader = LinearGradientShader(
      from = Offset.Zero,
      to = Offset(0f, height),
      colors = listOf(colors.first, colors.second)
    )
    pathEffect = cornerPathEffect(radius)
  }
  drawContext.canvas.drawPath(path, paint)
}