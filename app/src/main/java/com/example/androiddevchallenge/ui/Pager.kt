/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("Pager")

package com.example.androiddevchallenge.ui

import android.util.Log
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.animation.core.*
import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * Library-wide switch to turn on debug logging.
 */
internal const val DebugLog = false

private const val LogTag = "Pager"

/**
 * This attempts to mimic ViewPager's custom scroll interpolator. It's not a perfect match
 * (and we may not want it to be), but this seem to match in terms of scroll duration and 'feel'
 */
private const val SnapSpringStiffness = 2750f

@Immutable
private data class PageData(val page: Int) : ParentDataModifier {
  override fun Density.modifyParentData(parentData: Any?): Any = this@PageData
}

private val Measurable.page: Int
  get() = (parentData as? PageData)?.page ?: error("No PageData for measurable $this")

/**
 * Contains the default values used by [HorizontalPager] and [VerticalPager].
 */
object PagerDefaults {
  /**
   * Create and remember default [FlingBehavior] that will represent the scroll curve.
   *
   * @param state The [PagerState] to update.
   * @param decayAnimationSpec The decay animation spec to use for decayed flings.
   * @param snapAnimationSpec The animation spec to use when snapping.
   */
  @Composable
  fun defaultPagerFlingConfig(
    state: PagerState,
    decayAnimationSpec: DecayAnimationSpec<Float> = defaultDecayAnimationSpec(),
    snapAnimationSpec: AnimationSpec<Float> = spring(stiffness = SnapSpringStiffness),
  ): FlingBehavior = remember(state, decayAnimationSpec, snapAnimationSpec) {
    object : FlingBehavior {
      override suspend fun ScrollScope.performFling(
        initialVelocity: Float
      ): Float = state.fling(
        initialVelocity = -initialVelocity,
        decayAnimationSpec = decayAnimationSpec,
        snapAnimationSpec = snapAnimationSpec,
        scrollBy = { deltaPixels -> -scrollBy(-deltaPixels) },
      )
    }
  }
}

/**
 * A horizontally scrolling layout that allows users to flip between items to the left and right.
 *
 * This layout allows the setting of the [offscreenLimit], which defines the number of pages that
 * should be retained on either side of the current page. Pages beyond this limit will be
 * recreated as needed. This value defaults to `1`, but can be increased to enable pre-loading
 * of more content.
 *
 * @sample com.google.accompanist.sample.pager.HorizontalPagerSample
 *
 * @param state the state object to be used to control or observe the pager's state.
 * @param modifier the modifier to apply to this layout.
 * @param reverseLayout reverse the direction of scrolling and layout, when `true` items will be
 * composed from the end to the start and [PagerState.currentPage] == 0 will mean
 * the first item is located at the end.
 * @param offscreenLimit the number of pages that should be retained on either side of the
 * current page. This value is required to be `1` or greater.
 * @param flingBehavior logic describing fling behavior.
 * @param content a block which describes the content. Inside this block you can reference
 * [PagerScope.currentPage] and other properties in [PagerScope].
 */
@Composable
fun HorizontalPager(
  state: PagerState,
  modifier: Modifier = Modifier,
  reverseLayout: Boolean = false,
  @IntRange(from = 1) offscreenLimit: Int = 1,
  flingBehavior: FlingBehavior = PagerDefaults.defaultPagerFlingConfig(state),
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
  content: @Composable PagerScope.(page: Int) -> Unit,
) {
  Pager(
    state = state,
    modifier = modifier,
    isVertical = false,
    reverseLayout = reverseLayout,
    offscreenLimit = offscreenLimit,
    flingBehavior = flingBehavior,
    verticalAlignment = verticalAlignment,
    horizontalAlignment = horizontalAlignment,
    content = content
  )
}

/**
 * A vertically scrolling layout that allows users to flip between items to the top and bottom.
 *
 * This layout allows the setting of the [offscreenLimit], which defines the number of pages that
 * should be retained on either side of the current page. Pages beyond this limit will be
 * recreated as needed. This value defaults to `1`, but can be increased to enable pre-loading
 * of more content.
 *
 * @sample com.google.accompanist.sample.pager.VerticalPagerSample
 *
 * @param state the state object to be used to control or observe the pager's state.
 * @param modifier the modifier to apply to this layout.
 * @param reverseLayout reverse the direction of scrolling and layout, when `true` items will be
 * composed from the bottom to the top and [PagerState.currentPage] == 0 will mean
 * the first item is located at the bottom.
 * @param offscreenLimit the number of pages that should be retained on either side of the
 * current page. This value is required to be `1` or greater.
 * @param flingBehavior logic describing fling behavior.
 * @param content a block which describes the content. Inside this block you can reference
 * [PagerScope.currentPage] and other properties in [PagerScope].
 */
@Composable
fun VerticalPager(
  state: PagerState,
  modifier: Modifier = Modifier,
  reverseLayout: Boolean = false,
  @IntRange(from = 1) offscreenLimit: Int = 1,
  flingBehavior: FlingBehavior = PagerDefaults.defaultPagerFlingConfig(state),
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
  content: @Composable PagerScope.(page: Int) -> Unit,
) {
  Pager(
    state = state,
    modifier = modifier,
    isVertical = true,
    reverseLayout = reverseLayout,
    offscreenLimit = offscreenLimit,
    verticalAlignment = verticalAlignment,
    horizontalAlignment = horizontalAlignment,
    flingBehavior = flingBehavior,
    content = content
  )
}

@Composable
internal fun Pager(
  state: PagerState,
  modifier: Modifier,
  reverseLayout: Boolean,
  isVertical: Boolean,
  verticalAlignment: Alignment.Vertical,
  horizontalAlignment: Alignment.Horizontal,
  @IntRange(from = 1) offscreenLimit: Int,
  flingBehavior: FlingBehavior,
  content: @Composable PagerScope.(page: Int) -> Unit,
) {
  require(offscreenLimit >= 1) { "offscreenLimit is required to be >= 1" }

  val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
  val reverseDirection = if (isRtl) !reverseLayout else reverseLayout

  val coroutineScope = rememberCoroutineScope()
  val semanticsAxisRange = remember(state, reverseDirection) {
    ScrollAxisRange(
      value = { state.currentPage + state.currentPageOffset },
      maxValue = { state.lastPageIndex.toFloat() },
    )
  }
  val semantics = Modifier.semantics {
    horizontalScrollAxisRange = semanticsAxisRange
    // Hook up scroll actions to our state
    scrollBy { x, y ->
      coroutineScope.launch {
        if (isVertical) {
          state.scrollBy(if (reverseDirection) y else -y)
        } else {
          state.scrollBy(if (reverseDirection) x else -x)
        }
      }
      true
    }
    // Treat this as a selectable group
    selectableGroup()
  }

  val scrollable = Modifier.scrollable(
    orientation = if (isVertical) Orientation.Vertical else Orientation.Horizontal,
    flingBehavior = flingBehavior,
    reverseDirection = reverseDirection,
    state = state,
  )

  Layout(
    modifier = modifier
      .then(semantics)
      .then(scrollable)
      .clipToBounds(),
    content = {
      val firstPage = (state.currentPage - offscreenLimit).coerceAtLeast(0)
      val lastPage = (state.currentPage + offscreenLimit).coerceAtMost(state.lastPageIndex)

      if (DebugLog) {
        Log.d(
          LogTag,
          "Content: firstPage:$firstPage, " +
            "current:${state.currentPage}, " +
            "lastPage:$lastPage"
        )
      }

      for (page in firstPage..lastPage) {
        key(page) {
          val itemSemantics = Modifier.semantics {
            this.selected = page == state.currentPage
          }
          Box(
            contentAlignment = Alignment.Center,
            modifier = itemSemantics.then(PageData(page))
          ) {
            val scope = remember(this, state) {
              PagerScopeImpl(this, state)
            }
            scope.content(page)
          }
        }
      }
    },
  ) { measurables, constraints ->
    layout(constraints.maxWidth, constraints.maxHeight) {
      val currentPage = state.currentPage
      val offset = state.currentPageOffset
      val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)

      measurables.forEach {
        val placeable = it.measure(childConstraints)
        val page = it.page

        val xCenterOffset = horizontalAlignment.align(
          size = placeable.width,
          space = constraints.maxWidth,
          // We pass in Ltr here since we use placeRelative below.  If we use the
          // actual layoutDirection, placeRelative() will negate any difference.
          layoutDirection = LayoutDirection.Ltr,
        )
        val yCenterOffset = verticalAlignment.align(
          size = placeable.height,
          space = constraints.maxHeight
        )

        var yItemOffset = 0
        var xItemOffset = 0

        if (isVertical) {
          if (currentPage == page) {
            state.pageSize = placeable.height
          }
          yItemOffset = ((page - currentPage - offset) * placeable.height).roundToInt()
        } else {
          if (currentPage == page) {
            state.pageSize = placeable.width
          }
          xItemOffset = ((page - currentPage - offset) * placeable.width).roundToInt()
        }

        placeable.placeRelative(
          x = xCenterOffset + xItemOffset,
          y = yCenterOffset + yItemOffset,
        )
      }
    }
  }
}

/**
 * Scope for [HorizontalPager] content.
 */
@Stable
interface PagerScope : BoxScope {
  /**
   * Returns the current selected page
   */
  val currentPage: Int

  /**
   * Returns the current selected page offset
   */
  val currentPageOffset: Float
}

private class PagerScopeImpl(
  private val boxScope: BoxScope,
  private val state: PagerState,
) : PagerScope, BoxScope by boxScope {
  override val currentPage: Int
    get() = state.currentPage

  override val currentPageOffset: Float
    get() = state.currentPageOffset
}


/**
 * Creates a [PagerState] that is remembered across compositions.
 *
 * Changes to the provided values for [initialPage] and [initialPageOffset] will **not** result
 * in the state being recreated or changed in any way if it has already been created.
 * Changes to [pageCount] will result in the [PagerState] being updated.
 *
 * @param pageCount the value for [PagerState.pageCount]
 * @param initialPage the initial value for [PagerState.currentPage]
 * @param initialPageOffset the initial value for [PagerState.currentPageOffset]
 */
@Composable
fun rememberPagerState(
  @IntRange(from = 0) pageCount: Int,
  @IntRange(from = 0) initialPage: Int = 0,
  @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
): PagerState = rememberSaveable(saver = PagerState.Saver) {
  PagerState(
    pageCount = pageCount,
    currentPage = initialPage,
    currentPageOffset = initialPageOffset,
  )
}.apply {
  this.pageCount = pageCount
}

/**
 * A state object that can be hoisted to control and observe scrolling for [HorizontalPager].
 *
 * In most cases, this will be created via [rememberPagerState].
 *
 * @param pageCount the initial value for [PagerState.pageCount]
 * @param currentPage the initial value for [PagerState.currentPage]
 * @param currentPageOffset the initial value for [PagerState.currentPageOffset]
 */
@Stable
class PagerState(
  @IntRange(from = 0) pageCount: Int,
  @IntRange(from = 0) currentPage: Int = 0,
  @FloatRange(from = 0.0, to = 1.0) currentPageOffset: Float = 0f,
) : ScrollableState {
  private var _pageCount by mutableStateOf(pageCount)
  private var _currentPage by mutableStateOf(currentPage)
  private val _currentPageOffset = mutableStateOf(currentPageOffset)
  internal var pageSize by mutableStateOf(0)

  /**
   * The page position, as a float value between `0 until pageSize`
   */
  private val globalPosition: Float
    get() = currentPage + currentPageOffset

  /**
   * The ScrollableController instance. We keep it as we need to call stopAnimation on it once
   * we reached the end of the list.
   */
  private val scrollableState = ScrollableState { deltaPixels ->
    // scrollByOffset expects values in an opposite sign to what we're passed, so we need
    // to negate the value passed in, and the value returned.
    val size = pageSize.coerceAtLeast(1)
    -scrollByOffset(-deltaPixels / size) * size
  }

  init {
    require(pageCount >= 0) { "pageCount must be >= 0" }
    requireCurrentPage(currentPage, "currentPage")
    requireCurrentPageOffset(currentPageOffset, "currentPageOffset")
  }

  /**
   * The number of pages to display.
   */
  @get:IntRange(from = 0)
  var pageCount: Int
    get() = _pageCount
    set(@IntRange(from = 0) value) {
      require(value >= 0) { "pageCount must be >= 0" }
      _pageCount = value
      currentPage = currentPage.coerceIn(0, lastPageIndex)
    }

  internal val lastPageIndex: Int
    get() = (pageCount - 1).coerceAtLeast(0)

  /**
   * The index of the currently selected page.
   *
   * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
   */
  @get:IntRange(from = 0)
  var currentPage: Int
    get() = _currentPage
    private set(value) {
      _currentPage = value.coerceIn(0, lastPageIndex)
    }

  /**
   * The current offset from the start of [currentPage], as a fraction of the page width.
   *
   * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
   */
  @get:FloatRange(from = 0.0, to = 1.0)
  var currentPageOffset: Float
    get() = _currentPageOffset.value
    private set(value) {
      _currentPageOffset.value = value.coerceIn(
        minimumValue = 0f,
        maximumValue = if (currentPage == lastPageIndex) 0f else 1f,
      )
    }

  /**
   * Animate (smooth scroll) to the given page.
   *
   * Cancels the currently running scroll, if any, and suspends until the cancellation is
   * complete.
   *
   * @param page the page to snap to. Must be between 0 and [pageCount] (inclusive).
   * @param pageOffset the percentage of the page width to offset, from the start of [page]
   * @param initialVelocity Initial velocity in pixels per second, or `0f` to not use a start velocity.
   * Must be in the range 0f..1f.
   */
  suspend fun animateScrollToPage(
    @IntRange(from = 0) page: Int,
    @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
    initialVelocity: Float = 0f,
  ) {
    requireCurrentPage(page, "page")
    requireCurrentPageOffset(pageOffset, "pageOffset")

    if (page == currentPage) return

    // We don't specifically use the ScrollScope's scrollBy, but
    // we do want to use it's mutex
    scroll {
      animateToPage(
        page = page.coerceIn(0, lastPageIndex),
        pageOffset = pageOffset.coerceIn(0f, 1f),
        initialVelocity = initialVelocity,
      )
    }
  }

  /**
   * Instantly brings the item at [page] to the middle of the viewport, offset by [pageOffset]
   * percentage of page width.
   *
   * Cancels the currently running scroll, if any, and suspends until the cancellation is
   * complete.
   *
   * @param page the page to snap to. Must be between 0 and [pageCount] (inclusive).
   * @param pageOffset the percentage of the page width to offset, from the start of [page].
   * Must be in the range 0f..1f.
   */
  suspend fun scrollToPage(
    @IntRange(from = 0) page: Int,
    @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
  ) {
    requireCurrentPage(page, "page")
    requireCurrentPageOffset(pageOffset, "pageOffset")

    // We don't specifically use the ScrollScope's scrollBy(), but
    // we do want to use it's mutex
    scroll {
      currentPage = page
      currentPageOffset = pageOffset
    }
  }

  private fun snapToNearestPage() {
    if (DebugLog) {
      Log.d(LogTag, "snapToNearestPage. currentPage:$currentPage, offset:$currentPageOffset")
    }
    currentPage += currentPageOffset.roundToInt()
    currentPageOffset = 0f
  }

  private suspend fun animateToPage(
    page: Int,
    pageOffset: Float = 0f,
    animationSpec: AnimationSpec<Float> = spring(),
    initialVelocity: Float = 0f,
  ) {
    animate(
      initialValue = globalPosition,
      targetValue = page + pageOffset,
      initialVelocity = initialVelocity,
      animationSpec = animationSpec
    ) { value, _ ->
      updateFromGlobalPosition(value)
    }
    snapToNearestPage()
  }

  private fun determineSpringBackOffset(
    velocity: Float,
    offset: Float = currentPageOffset,
  ): Float = when {
    // If the velocity is greater than 1 page per second (velocity is px/s), spring
    // in the relevant direction
    velocity >= pageSize -> 1f
    velocity <= -pageSize -> 0f
    // If the offset exceeds the scroll threshold (in either direction), we want to
    // move to the next/previous item
    offset < 0.5f -> 0f
    else -> 1f
  }

  private fun updateFromGlobalPosition(position: Float) {
    currentPage = floor(position).toInt()
    currentPageOffset = position - currentPage
  }

  /**
   * Scroll by the pager with the given [deltaOffset].
   *
   * @param deltaOffset delta in offset values (0f..1f). Values > 0 signify scrolls
   * towards the end of the pager, and values < 0 towards the start.
   * @return any unconsumed [deltaOffset]
   */
  private fun scrollByOffset(deltaOffset: Float): Float {
    val current = globalPosition
    val target = (current + deltaOffset).coerceIn(0f, lastPageIndex.toFloat())
    updateFromGlobalPosition(target)

    if (DebugLog) {
      Log.d(
        LogTag,
        "scrollByOffset. delta:%.4f, new-page:%d, new-offset:%.4f"
          .format(deltaOffset, currentPage, currentPageOffset),
      )
    }

    return deltaOffset - (target - current)
  }

  /**
   * Fling the pager with the given [initialVelocity]. [scrollBy] will called whenever a
   * scroll change is required by the fling.
   *
   * @param initialVelocity velocity in pixels per second. Values > 0 signify flings
   * towards the end of the pager, and values < 0 sign flings towards the start.
   * @param decayAnimationSpec The decay animation spec to use for decayed flings.
   * @param snapAnimationSpec The animation spec to use when snapping.
   * @param scrollBy block which is called when a scroll is required. Positive values passed in
   * signify scrolls towards the end of the pager, and values < 0 towards the start.
   * @return any remaining velocity after the scroll has finished.
   */
  internal suspend fun fling(
    initialVelocity: Float,
    decayAnimationSpec: DecayAnimationSpec<Float> = exponentialDecay(),
    snapAnimationSpec: AnimationSpec<Float> = spring(),
    scrollBy: (Float) -> Float,
  ): Float {
    // We calculate the target offset using pixels, rather than using the offset
    val targetOffset = decayAnimationSpec.calculateTargetValue(
      initialValue = currentPageOffset * pageSize,
      initialVelocity = initialVelocity
    ) / pageSize

    if (DebugLog) {
      Log.d(
        LogTag,
        "fling. velocity:%.4f, page: %d, offset:%.4f, targetOffset:%.4f"
          .format(initialVelocity, currentPage, currentPageOffset, targetOffset)
      )
    }

    var lastVelocity: Float = initialVelocity

    // If the animation can naturally end outside of current page bounds, we will
    // animate with decay.
    if (targetOffset.absoluteValue >= 1) {
      // Animate with the decay animation spec using the fling velocity

      val targetPage = when {
        targetOffset > 0 -> (currentPage + 1).coerceAtMost(lastPageIndex)
        else -> currentPage
      }

      AnimationState(
        initialValue = currentPageOffset * pageSize,
        initialVelocity = initialVelocity
      ).animateDecay(decayAnimationSpec) {
        if (DebugLog) {
          Log.d(
            LogTag,
            "fling. decay. value:%.4f, page: %d, offset:%.4f"
              .format(value, currentPage, currentPageOffset)
          )
        }

        // Keep track of velocity
        lastVelocity = velocity

        // Now scroll..
        val coerced = value.coerceIn(0f, pageSize.toFloat())
        scrollBy(coerced - (currentPageOffset * pageSize))

        // If we've scroll our target page (or beyond it), cancel the animation
        val pastStartBound = initialVelocity < 0 &&
          (currentPage < targetPage || (currentPage == targetPage && currentPageOffset == 0f))
        val pastEndBound = initialVelocity > 0 &&
          (currentPage > targetPage || (currentPage == targetPage && currentPageOffset > 0f))

        if (pastStartBound || pastEndBound) {
          // If we reach the bounds of the allowed offset, cancel the animation
          cancelAnimation()
          currentPage = targetPage
          currentPageOffset = 0f
        }
      }
    } else {
      // Otherwise we animate to the next item, or spring-back depending on the offset
      val targetPosition = currentPage + determineSpringBackOffset(
        velocity = initialVelocity,
        offset = targetOffset
      )
      animate(
        initialValue = globalPosition * pageSize,
        targetValue = targetPosition * pageSize,
        initialVelocity = initialVelocity,
        animationSpec = snapAnimationSpec,
      ) { value, velocity ->
        scrollBy(value - (globalPosition * pageSize))
        // Keep track of velocity
        lastVelocity = velocity
      }
    }

    snapToNearestPage()
    return lastVelocity
  }

  override val isScrollInProgress: Boolean
    get() = scrollableState.isScrollInProgress

  override fun dispatchRawDelta(delta: Float): Float {
    return scrollableState.dispatchRawDelta(delta)
  }

  override suspend fun scroll(
    scrollPriority: MutatePriority,
    block: suspend ScrollScope.() -> Unit
  ) {
    scrollableState.scroll(scrollPriority, block)
  }

  override fun toString(): String = "PagerState(" +
    "pageCount=$pageCount, " +
    "currentPage=$currentPage, " +
    "currentPageOffset=$currentPageOffset" +
    ")"

  private fun requireCurrentPage(value: Int, name: String) {
    if (pageCount == 0) {
      require(value == 0) { "$name must be 0 when pageCount is 0" }
    } else {
      require(value in 0 until pageCount) {
        "$name must be >= 0 and < pageCount"
      }
    }
  }

  private fun requireCurrentPageOffset(value: Float, name: String) {
    if (pageCount == 0) {
      require(value == 0f) { "$name must be 0f when pageCount is 0" }
    } else {
      require(value in 0f..1f) { "$name must be >= 0 and <= 1" }
    }
  }

  companion object {
    /**
     * The default [Saver] implementation for [PagerState].
     */
    val Saver: Saver<PagerState, *> = listSaver(
      save = { listOf<Any>(it.pageCount, it.currentPage, it.currentPageOffset) },
      restore = {
        PagerState(
          pageCount = it[0] as Int,
          currentPage = it[1] as Int,
          currentPageOffset = it[2] as Float
        )
      }
    )
  }
}