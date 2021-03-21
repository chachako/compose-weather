package com.example.androiddevchallenge.data

import com.github.promeg.pinyinhelper.Pinyin
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.FromJson

import com.squareup.moshi.ToJson
import java.util.*

/**
 * 关于城市的数据
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "code")
    val code: String,
    @Json(name = "name") @CityName
    val name: String,
    @Json(name = "provinceCode")
    val provinceCode: String
)

/** 我们在这里将 Json 中的城市名称与拼音互转 */
class CityNameAdapter {
    @FromJson @CityName
    fun getPinyin(name: String): String = Pinyin.toPinyin(name, "").toLowerCase().capitalize()

    @ToJson
    fun getChinese(@CityName name: String): String = TODO("Not supported yet.")
}

@JsonQualifier
annotation class CityName
