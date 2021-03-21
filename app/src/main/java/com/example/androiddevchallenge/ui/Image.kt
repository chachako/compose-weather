package com.example.androiddevchallenge.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.request.ImageRequest
import dev.chrisbanes.accompanist.coil.CoilImageDefaults
import dev.chrisbanes.accompanist.imageloading.DefaultRefetchOnSizeChangeLambda
import dev.chrisbanes.accompanist.imageloading.EmptyRequestCompleteLambda
import dev.chrisbanes.accompanist.imageloading.ImageLoadState

@Composable
fun Image(
  @DrawableRes id: Int,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  alpha: Float = DefaultAlpha,
  colorFilter: ColorFilter? = null
) {
  androidx.compose.foundation.Image(
    painter = painterResource(id),
    contentDescription, modifier, alignment, contentScale, alpha, colorFilter
  )
}

@Composable
fun CoilImage(
  data: Any,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  colorFilter: ColorFilter? = null,
  fadeIn: Boolean = false,
  requestBuilder: (ImageRequest.Builder.(size: IntSize) -> ImageRequest.Builder)? = null,
  imageLoader: ImageLoader = CoilImageDefaults.defaultImageLoader(),
  shouldRefetchOnSizeChange: (currentResult: ImageLoadState, size: IntSize) -> Boolean = DefaultRefetchOnSizeChangeLambda,
  onRequestCompleted: (ImageLoadState) -> Unit = EmptyRequestCompleteLambda,
  error: @Composable (BoxScope.(ImageLoadState.Error) -> Unit)? = null,
  loading: @Composable (BoxScope.() -> Unit)? = null,
) {
  dev.chrisbanes.accompanist.coil.CoilImage(
    data,
    contentDescription,
    modifier.sizeIn(minWidth = 1.dp, minHeight = 1.dp),
    alignment,
    contentScale,
    colorFilter,
    fadeIn,
    requestBuilder,
    imageLoader,
    shouldRefetchOnSizeChange,
    onRequestCompleted,
    error,
    loading
  )
}