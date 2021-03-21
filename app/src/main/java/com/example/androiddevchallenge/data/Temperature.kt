package com.example.androiddevchallenge.data

/**
 * @author 凛 (https://github.com/RinOrz)
 *
 * @property realtime 实时温度
 * @property max 最高温度
 * @property min 最低温度
 */
data class Temperature(
  val realtime: Double,
  val max: Double,
  val min: Double
)