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

import com.example.androiddevchallenge.R

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
) {
    companion object {

        /** 获取特定的温度数字图标 */
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
    }
}
