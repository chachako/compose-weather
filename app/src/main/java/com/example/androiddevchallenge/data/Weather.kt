package com.example.androiddevchallenge.data

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.GradientColor

/**
 * 天气数据
 *
 * @author 凛 (https://github.com/RinOrz)
 *
 * @param forecastDays 每一天的天气预报信息
 */
data class Weather(
  val city: City,
  val updateTime: String,
  val forecastDays: List<Info>
) {
  /**
   * 列表第一个值永远是今天
   */
  val today get() = forecastDays[0]

  /**
   * 一整天的天气信息
   *
   * @param temperature 今天的平均温度
   * @param hours 今天的所有时间段的预报
   */
  data class Info(
    val temperature: Temperature,
    val kind: Kind,
    val aqi: AQI,
    val hours: List<Info>,
    val forecastTime: Long? = null,
  )

  @Keep
  enum class Kind(@DrawableRes val icon: Int, val colors: GradientColor) {
    Sunny(R.mipmap.sunny, Color(0xFFE15A5A) to Color(0xFFC8A471)),
    Cloudy(R.mipmap.cloudy, Color(0xFF67A960) to Color(0xFF8E7F45)),
    Shower(R.mipmap.shower, Color(0xFF945AF8) to Color(0xFFDE616D)),
    Thundershower(R.mipmap.thundershower, Color(0xFF3E6FF0) to Color(0xFF35C1B2)),
    Snow(R.mipmap.snow, Color(0xFF3AAAD9) to Color(0xFFA394C5)),
    Fog(R.mipmap.fog, Color(0xFFEA9827) to Color(0xFFB7B382)),
    Sandstorm(R.mipmap.sandstorm, Color(0xFFB57B45) to Color(0xFF9A8787)),
    Haze(R.mipmap.haze, Color(0xFF5B687A) to Color(0xFFB6BBC3)),
  }
}