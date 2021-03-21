package com.example.androiddevchallenge.data

data class AQI(
  val value: Int,
  val pm25: Int,
  val pm10: Int,
  val so2: Int,
  val no2: Int,
  val co: Int,
  val o3: Int,
) {
  val level: Level
    get() = when (value) {
      in Level.Excellent.range -> Level.Excellent
      in Level.Good.range -> Level.Good
      in Level.LightlyPolluted.range -> Level.LightlyPolluted
      in Level.ModeratelyPolluted.range -> Level.ModeratelyPolluted
      in Level.HeavilyPolluted.range -> Level.HeavilyPolluted
      else -> Level.SeverelyPolluted
    }

  /** TODO Localization */
  enum class Level(val range: IntRange, val category: String, val healthImplications: String) {
    Excellent(0..50, "Excellent", "No health implications."),
    Good(
      51..100,
      "Good",
      "Some pollutants may slightly affect very few hypersensitive individuals."
    ),
    LightlyPolluted(
      101..150,
      "Lightly Polluted",
      "Healthy people may experience slight irritations and sensitive individuals will be slightly affected to a larger extent."
    ),
    ModeratelyPolluted(
      151..200,
      "Moderately Polluted",
      "Sensitive individuals will experience more serious conditions."
    ),
    HeavilyPolluted(
      201..300,
      "Heavily Polluted",
      "Healthy people will commonly show symptoms. People with respiratory or heart diseases will be significantly affected and will experience reduced endurance in activities."
    ),
    SeverelyPolluted(
      301..Int.MAX_VALUE,
      "Severely Polluted",
      "Healthy people will experience reduced endurance in activities and may also show noticeably strong symptoms. Other illnesses may be triggered in healthy people. Elders and the sick should remain indoors and avoid exercise. Healthy individuals should avoid outdoor activities."
    ),
  }
}