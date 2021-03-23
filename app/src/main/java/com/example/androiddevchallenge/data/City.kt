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
package com.example.androiddevchallenge.data

import com.github.promeg.pinyinhelper.Pinyin
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * 关于城市的数据
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "code")
    val code: Int,
    @Json(name = "name") @CityName
    val name: String,
    @Json(name = "provinceCode")
    val provinceCode: Int
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
