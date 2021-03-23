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
package com.example.androiddevchallenge.ui.details.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.LocalPadding
import com.example.androiddevchallenge.ui.theme.currentShapes

@Composable
fun SunriseSunset() {
    val contentColor = LocalContentColor.current
    Row(
        modifier = Modifier
            .padding(
                horizontal = LocalPadding.current.horizontal,
                vertical = LocalPadding.current.vertical
            )
            .fillMaxWidth()
            .height(110.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        contentColor.copy(alpha = 0.04f),
                        contentColor.copy(alpha = 0.01f)
                    )
                ),
                shape = currentShapes.small
            )
    ) {
        Line()
    }
}

@Composable
private fun Line() {
    val contentColor = LocalContentColor.current
    val sunRadius = 10f
    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val trackRadius = 1.0f * (width - 2 * sunRadius) / 2
        val expectedHeight = trackRadius + sunRadius
        val trackPaint = Paint().apply {
            color = contentColor
            style = PaintingStyle.Stroke
            strokeWidth = 2f
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 1f)
        }
        drawContext.canvas.drawArc(
            sunRadius,
            sunRadius,
            width - sunRadius,
            expectedHeight,
            180f, 180f, false, trackPaint
        )

        // Mask
        val maskPath = Path().apply {
            moveTo(0f, height)
            arcTo(Rect(Offset.Zero, Offset(width, height)), 180f, 0f, true)
            lineTo(0f, height)
            close()
        }
        drawPath(maskPath, Brush.verticalGradient(listOf(contentColor.copy(0.1f), contentColor.copy(0f))))
        drawContext.canvas
//    prepareShadowPaint()
//    canvas.save()
//    val path = Path()
//    val endY = mBoardRectF.bottom
//    val rectF = RectF(
//      mBoardRectF.left,
//      mBoardRectF.top,
//      mBoardRectF.right,
//      mBoardRectF.bottom + mBoardRectF.height()
//    )
//    val curPointX = mBoardRectF.left + mTrackRadius - mTrackRadius * Math.cos(Math.PI * mRatio)
//      .toFloat()
//    path.moveTo(0f, endY)
//    path.arcTo(rectF, 180f, 180 * mRatio)
//    path.lineTo(curPointX, endY)
//    path.close()
//    canvas.drawPath(path, mShadowPaint!!)
//    canvas.restore()
    }
}

/**
 * A view which can show sunrise and sunset animation
 */
// class SunriseSunsetView : View {
//  /**
//   * 当前日出日落比率, mRatio < 0: 未日出, mRatio > 1 已日落
//   */
//  private var mRatio = 0f
//  private var mTrackPaint // 绘制半圆轨迹的Paint
//    : Paint? = null
//  private var mTrackColor = DEFAULT_TRACK_COLOR // 轨迹的颜色
//  private var mTrackWidth = DEFAULT_TRACK_WIDTH_PX // 轨迹的宽度
//
//  // 轨迹的PathEffect
//  private var mTrackPathEffect: PathEffect = DashPathEffect(floatArrayOf(15f, 15f), 1)
//
//  // 轨迹圆的半径
//  private var mTrackRadius = 0f
//  private var mShadowPaint // 绘制日出日落阴影的Paint
//    : Paint? = null
//  private var mShadowColor = DEFAULT_SHADOW_COLOR // 阴影颜色
//  private var mSunPaint // 绘制太阳的Paint
//    : Paint? = null
//  private var mSunColor = DEFAULT_SUN_COLOR // 太阳颜色
//  var sunRadius = DEFAULT_SUN_RADIUS_PX // 太阳半径
//    .toFloat()
//  private var mSunPaintStyle = Paint.Style.FILL // 太阳Paint样式,默认FILL
//  private var mLabelPaint // 绘制日出日落时间的Paint
//    : TextPaint? = null
//  private var mLabelTextSize = DEFAULT_LABEL_TEXT_SIZE // 标签文字大小
//  private var mLabelTextColor = DEFAULT_LABEL_TEXT_COLOR // 标签颜色
//  private var mLabelVerticalOffset = DEFAULT_LABEL_VERTICAL_OFFSET_PX // 竖直方向间距
//  private var mLabelHorizontalOffset = DEFAULT_LABEL_HORIZONTAL_OFFSET_PX // 水平方向间距
//
//  /**
//   * 日出时间
//   */
//  private var mSunriseTime: Time? = null
//
//  /**
//   * 日落时间
//   */
//  private var mSunsetTime: Time? = null
//
//  /**
//   * 绘图区域
//   */
//  private val mBoardRectF = RectF()
//
//  // Label Formatter - Default is a Simple label formatter.
//  private var mLabelFormatter: SunriseSunsetLabelFormatter = SimpleSunriseSunsetLabelFormatter()
//
//  constructor(context: Context?) : super(context) {
//    init()
//  }
//
//  @JvmOverloads
//  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
//    context,
//    attrs,
//    defStyleAttr
//  ) {
//    val a = context.obtainStyledAttributes(attrs, R.styleable.SunriseSunsetView, defStyleAttr, 0)
//    if (a != null) {
//      mTrackColor = a.getColor(R.styleable.SunriseSunsetView_ssv_track_color, DEFAULT_TRACK_COLOR)
//      mTrackWidth = a.getDimensionPixelSize(
//        R.styleable.SunriseSunsetView_ssv_track_width,
//        DEFAULT_TRACK_WIDTH_PX
//      )
//      mShadowColor = a.getColor(
//        R.styleable.SunriseSunsetView_ssv_shadow_color,
//        DEFAULT_SHADOW_COLOR
//      )
//      mSunColor = a.getColor(R.styleable.SunriseSunsetView_ssv_sun_color, DEFAULT_SUN_COLOR)
//      sunRadius = a
//        .getDimensionPixelSize(R.styleable.SunriseSunsetView_ssv_sun_radius, DEFAULT_SUN_RADIUS_PX)
//        .toFloat()
//      mLabelTextColor = a.getColor(
//        R.styleable.SunriseSunsetView_ssv_label_text_color,
//        DEFAULT_LABEL_TEXT_COLOR
//      )
//      mLabelTextSize = a.getDimensionPixelSize(
//        R.styleable.SunriseSunsetView_ssv_label_text_size,
//        DEFAULT_LABEL_TEXT_SIZE
//      )
//      mLabelVerticalOffset = a.getDimensionPixelOffset(
//        R.styleable.SunriseSunsetView_ssv_label_vertical_offset,
//        DEFAULT_LABEL_VERTICAL_OFFSET_PX
//      )
//      mLabelHorizontalOffset = a.getDimensionPixelOffset(
//        R.styleable.SunriseSunsetView_ssv_label_horizontal_offset,
//        DEFAULT_LABEL_HORIZONTAL_OFFSET_PX
//      )
//      a.recycle()
//    }
//    init()
//  }
//
//  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//    val paddingRight = paddingRight
//    val paddingLeft = paddingLeft
//    val paddingTop = paddingTop
//    val paddingBottom = paddingBottom
//    val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
//    var widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
//
//    // 处理wrap_content这种情况
//    if (widthSpecMode == MeasureSpec.AT_MOST) {
//      widthSpecSize = paddingLeft + paddingRight + MINIMAL_TRACK_RADIUS_PX * 2 + sunRadius.toInt() * 2
//    }
//    mTrackRadius = 1.0f * (widthSpecSize - paddingLeft - paddingRight - 2 * sunRadius) / 2
//    val expectedHeight = (mTrackRadius + sunRadius + paddingBottom + paddingTop).toInt()
//    mBoardRectF[paddingLeft + sunRadius, paddingTop + sunRadius, widthSpecSize - paddingRight - sunRadius] = (expectedHeight - paddingBottom).toFloat()
//    setMeasuredDimension(widthSpecSize, expectedHeight)
//  }
//
//  private fun init() {
//    // 初始化半圆轨迹的画笔
//    mTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    mTrackPaint!!.style = Paint.Style.STROKE // 画笔的样式为线条
//    prepareTrackPaint()
//
//    // 初始化日出日落阴影的画笔
//    mShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    mShadowPaint!!.style = Paint.Style.FILL_AND_STROKE
//    prepareShadowPaint()
//
//    // 初始化太阳的Paint
//    mSunPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    mSunPaint!!.strokeWidth = DEFAULT_SUN_STROKE_WIDTH_PX.toFloat()
//    prepareSunPaint()
//    mLabelPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
//    prepareLabelPaint()
//  }
//
//  // 半圆轨迹的画笔
//  private fun prepareTrackPaint() {
//    mTrackPaint!!.color = mTrackColor
//    mTrackPaint!!.strokeWidth = mTrackWidth.toFloat()
//    mTrackPaint!!.pathEffect = mTrackPathEffect
//  }
//
//  // 阴影的画笔
//  private fun prepareShadowPaint() {
//    mShadowPaint!!.color = mShadowColor
//  }
//
//  // 太阳的画笔
//  private fun prepareSunPaint() {
//    mSunPaint!!.color = mSunColor
//    mSunPaint!!.strokeWidth = DEFAULT_SUN_STROKE_WIDTH_PX.toFloat()
//    mSunPaint!!.style = mSunPaintStyle
//  }
//
//  // 标签的画笔
//  private fun prepareLabelPaint() {
//    mLabelPaint!!.color = mLabelTextColor
//    mLabelPaint!!.textSize = mLabelTextSize.toFloat()
//  }
//
//  override fun onDraw(canvas: Canvas) {
//    super.onDraw(canvas)
//    drawSunTrack(canvas)
//    drawShadow(canvas)
//    drawSun(canvas)
//    drawSunriseSunsetLabel(canvas)
//  }
//
//  // 绘制太阳轨道（半圆）
//  private fun drawSunTrack(canvas: Canvas) {
//    prepareTrackPaint()
//    canvas.save()
//    val rectF = RectF(
//      mBoardRectF.left,
//      mBoardRectF.top,
//      mBoardRectF.right,
//      mBoardRectF.bottom + mBoardRectF.height()
//    )
//    canvas.drawArc(rectF, 180f, 180f, false, mTrackPaint!!)
//    canvas.restore()
//  }
//
//  // 绘制日出日落阴影部分
//  private fun drawShadow(canvas: Canvas) {
//    prepareShadowPaint()
//    canvas.save()
//    val path = Path()
//    val endY = mBoardRectF.bottom
//    val rectF = RectF(
//      mBoardRectF.left,
//      mBoardRectF.top,
//      mBoardRectF.right,
//      mBoardRectF.bottom + mBoardRectF.height()
//    )
//    val curPointX = mBoardRectF.left + mTrackRadius - mTrackRadius * Math.cos(Math.PI * mRatio)
//      .toFloat()
//    path.moveTo(0f, endY)
//    path.arcTo(rectF, 180f, 180 * mRatio)
//    path.lineTo(curPointX, endY)
//    path.close()
//    canvas.drawPath(path, mShadowPaint!!)
//    canvas.restore()
//  }
//
//  // 绘制太阳
//  private fun drawSun(canvas: Canvas) {
//    prepareSunPaint()
//    canvas.save()
//    val curPointX = mBoardRectF.left + mTrackRadius - mTrackRadius * Math.cos(Math.PI * mRatio)
//      .toFloat()
//    val curPointY = mBoardRectF.bottom - mTrackRadius * Math.sin(Math.PI * mRatio)
//      .toFloat()
//    canvas.drawCircle(curPointX, curPointY, sunRadius, mSunPaint!!)
//    canvas.restore()
//  }
//
//  // 绘制日出日落标签
//  private fun drawSunriseSunsetLabel(canvas: Canvas) {
//    if (mSunriseTime == null || mSunsetTime == null) {
//      return
//    }
//    prepareLabelPaint()
//    canvas.save()
//    // 绘制日出时间
//    val sunriseStr: String = mLabelFormatter.formatSunriseLabel(mSunriseTime)
//    mLabelPaint!!.textAlign = Paint.Align.LEFT
//    val metricsInt = mLabelPaint!!.fontMetricsInt
//    var baseLineX = mBoardRectF.left + sunRadius + mLabelHorizontalOffset
//    val baseLineY = mBoardRectF.bottom - metricsInt.bottom - mLabelVerticalOffset
//    canvas.drawText(sunriseStr, baseLineX, baseLineY, mLabelPaint!!)
//
//    // 绘制日落时间
//    mLabelPaint!!.textAlign = Paint.Align.RIGHT
//    val sunsetStr: String = mLabelFormatter.formatSunsetLabel(mSunsetTime)
//    baseLineX = mBoardRectF.right - sunRadius - mLabelHorizontalOffset
//    canvas.drawText(sunsetStr, baseLineX, baseLineY, mLabelPaint!!)
//    canvas.restore()
//  }
//
//  fun startAnimate() {
//    if (mSunriseTime == null || mSunsetTime == null) {
//      throw RuntimeException("You need to set both sunrise and sunset time before start animation")
//    }
//    val sunrise: Int = mSunriseTime.transformToMinutes()
//    val sunset: Int = mSunsetTime.transformToMinutes()
//    val calendar = Calendar.getInstance(Locale.getDefault())
//    val currentHour = calendar[Calendar.HOUR_OF_DAY]
//    val currentMinute = calendar[Calendar.MINUTE]
//    val currentTime: Int = currentHour * Time.MINUTES_PER_HOUR + currentMinute
//    var ratio = 1.0f * (currentTime - sunrise) / (sunset - sunrise)
//    ratio = if (ratio <= 0) 0 else if (ratio > 1.0f) 1 else ratio
//    val animator = ObjectAnimator.ofFloat(this, "ratio", 0f, ratio)
//    animator.duration = 1500L
//    animator.interpolator = LinearInterpolator()
//    animator.start()
//  }
//
//  companion object {
//    private const val DEFAULT_TRACK_COLOR = Color.WHITE
//    private const val DEFAULT_TRACK_WIDTH_PX = 4
//    private const val DEFAULT_SUN_COLOR = Color.YELLOW
//    private const val DEFAULT_SUN_RADIUS_PX = 20
//    private const val DEFAULT_SUN_STROKE_WIDTH_PX = 4
//    private val DEFAULT_SHADOW_COLOR = Color.parseColor("#32FFFFFF")
//    private const val DEFAULT_LABEL_TEXT_COLOR = Color.WHITE
//    private const val DEFAULT_LABEL_TEXT_SIZE = 40
//    private const val DEFAULT_LABEL_VERTICAL_OFFSET_PX = 5
//    private const val DEFAULT_LABEL_HORIZONTAL_OFFSET_PX = 20
//    private const val MINIMAL_TRACK_RADIUS_PX = 300 // 半圆轨迹最小半径
//  }
// }
