package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.data.AQI
import com.example.androiddevchallenge.data.City
import com.example.androiddevchallenge.data.Temperature
import com.example.androiddevchallenge.data.Weather
import com.meowbase.toolkit.formatTime
import com.meowbase.toolkit.getNextHour
import com.meowbase.toolkit.int
import com.meowbase.toolkit.isToday
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

/**
 * Providers all mock data to the compose-weather application.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
class MockRepository @Inject constructor() {

  fun getCityByLocation(cities: List<City>) = cities[cities.indices.random()]

  fun getWeather(city: City) = Weather(
    city = city,
    forecastDays = listOf(getWeatherInfo()) + getForecastNextDays(),
    updateTime = formatTime(
      pattern = "E HH:mm:ss",
      locale = Locale.ENGLISH // TODO Localization
    ),
  )

  private fun getForecastNextDays(): List<Weather.Info> = mutableListOf<Weather.Info>().apply {
    // 模拟接下来七天的天气
    repeat(7) { this += getWeatherInfo() }
  }

  private fun getWeatherInfo(): Weather.Info = Weather.Info(
    aqi = getAQI(),
    kind = getWeatherKind(),
    temperature = getTemperature(),
    hours = getHoursWeather(),
  )

  private fun getHoursWeather(): List<Weather.Info> {
    val hours = mutableListOf<Weather.Info>()
    val baseTemperature = getTemperature()

    // 模拟时段的预报
    fun getForecastInfo(forecastTime: Long): Weather.Info {
      val temperatureDiff = if (forecastTime.isToday) {
        (0..6).random() - 3
      } else {
        (0..12).random() - 6
      }
      return Weather.Info(
        aqi = getAQI(),
        kind = getWeatherKind(),
        // 模拟24小时内的细微温差
        temperature = baseTemperature.copy(realtime = baseTemperature.realtime + temperatureDiff),
        hours = emptyList(),
        forecastTime = forecastTime
      )
    }

    repeat(24) { hour ->
      hours += getForecastInfo(forecastTime = getNextHour(hour))
    }
    return hours
  }

  private fun getTemperature(): Temperature {
    val base = (100..152).random()
    val min = Random.nextDouble(base * 0.8, base * 0.96)
    val max = Random.nextDouble(base * 1.12, base * 1.15)
    val realtime = Random.nextDouble(min, max)
    return Temperature(
      realtime = realtime - 126,
      max = max - 126,
      min = min - 126
    )
  }

  private fun getWeatherKind() = when ((0..7).random()) {
    0 -> Weather.Kind.Sunny
    1 -> Weather.Kind.Cloudy
    2 -> Weather.Kind.Shower
    3 -> Weather.Kind.Thundershower
    4 -> Weather.Kind.Snow
    5 -> Weather.Kind.Fog
    6 -> Weather.Kind.Sandstorm
    7 -> Weather.Kind.Haze
    else -> TODO("未知天气")
  }

  private fun getAQI(): AQI {
    val index = (0..310).random()
    val pm25 = ((index / 2)..index + 80).random()
    val pm10 = ((index / 3)..((index * 0.88).int)).random()
    val no2 = ((index / 6)..(index / 3)).random()
    val so2 = (0..(index / 10)).random()
    val co = (0..(index / 12)).random()
    val o3 = (0..index).random()

    return AQI(index, pm25, pm10, so2, no2, co, o3)
  }
}