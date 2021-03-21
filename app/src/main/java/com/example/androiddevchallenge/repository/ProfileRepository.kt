/*
 * Copyright 2021 The Android Open Source Project
 * Copyright 2021 RinOrz (凛)
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
 * Github home page: https://github.com/RinOrz
 */
package com.example.androiddevchallenge.repository

import javax.inject.Inject

/**
 * 提供个人资料屏幕所需要的数据
 * Provides data to profile screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
class ProfileRepository @Inject constructor() {

  /**
   * 获取个人头像
   * Get personal avatar.
   *
   * TODO: Get data from DataStore.
   */
  fun getAvatar(): Any = "https://avatars.githubusercontent.com/u/58068445?s=460&u=87b1e18b75533c3ec88b1f5f34b33499d0e0897c&v=4"
}
