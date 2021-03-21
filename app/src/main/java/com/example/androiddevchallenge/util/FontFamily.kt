package com.example.androiddevchallenge.util

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontListFontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FontFamily.toTypeface(weight: FontWeight = FontWeight.Normal): Typeface? {
  val font = (this as? FontListFontFamily)?.fonts?.find { it.weight == weight }
  return font?.let { LocalFontLoader.current.load(it) } as? Typeface
}