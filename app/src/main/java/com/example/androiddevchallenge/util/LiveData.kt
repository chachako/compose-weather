package com.example.androiddevchallenge.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData

@Composable
fun <T> LiveData<T>.observeAsNonNullState(): State<T> = observeAsState(value!!)