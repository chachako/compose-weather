/*
 * Copyright 2021-2021 The Android Open Source Project
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
package com.example.androiddevchallenge.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry

/**
 * 从 [NavBackStackEntry] 中获取 [ViewModel]
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.viewModel(
    key: String? = null
): T = viewModel(
    key = key,
    factory = HiltViewModelFactory(
        context = LocalContext.current,
        navBackStackEntry = this
    )
)
