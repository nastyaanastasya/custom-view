package com.nastyaanastasya.v_geometry_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.nastyaanastasya.v_geometry_view.attrs.GeometryViewAttributes
import com.nastyaanastasya.v_geometry_view.attrs.GeometryViewAttributesReader
import com.nastyaanastasya.v_geometry_view.data.Coordinates
import com.nastyaanastasya.v_geometry_view.data.state.SavedState
import com.nastyaanastasya.v_geometry_view.utils.ViewSizeRandomizer as Random

private const val MAX_FIGURES_VALUE = 10

class GeometryView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = R.attr.geometryViewStyle,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var backgroundView: View
    private lateinit var counterText: TextView
    private lateinit var colors: IntArray
    private lateinit var paint: Paint

    protected lateinit var styleAttrs: GeometryViewAttributes

    private val coordinates = Coordinates(0f, 0f)
    private var counter = 0

    init {
        obtainAttrs()
        inflateLayout(context)
        findViews()
        applyAttrs()
        initPaint()
        setOnDrawAfterInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(styleAttrs.backgroundColor)

        if (isMaxFiguresReached()) {
            resetCounter()
        } else if (counter != 0) {
            drawRandomFigure(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (event.action == MotionEvent.ACTION_DOWN) {
                coordinates.x = it.rawX
                coordinates.y = it.rawY
                invalidate()
                updateCounter()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            state.counterValue?.let {
                counter = it
            }
            counterText.text = counter.toString()
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState()).apply {
            this.counterValue = counter
        }
    }

    private fun obtainAttrs() {
        context.withStyledAttributes(attrs, R.styleable.GeometryView, defStyleAttr) {
            styleAttrs = GeometryViewAttributesReader.read(context, this)
        }
    }

    private fun inflateLayout(context: Context) {
        inflate(context, R.layout.geometry_view, this)
    }

    private fun findViews() {
        counterText = findViewById(R.id.counter_text)
        backgroundView = findViewById(R.id.background_view)
    }

    private fun applyAttrs() {
        colors = styleAttrs.colors
    }

    private fun initPaint() {
        paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 4f
            strokeCap = Paint.Cap.ROUND
        }
        if (colors.size == 1) {
            paint.color = colors[0]
        }
    }

    private fun drawRandomFigure(canvas: Canvas) {
        setPaintColor()
        when (Random.getRandomValue() % 3) {
            0 -> drawCircle(canvas, Random.getRandomSize())
            1 -> drawSquare(canvas, Random.getRandomSize())
            2 -> drawRoundedCornerSquare(canvas, Random.getRandomSize())
        }
    }

    private fun drawCircle(canvas: Canvas, diameter: Float) {
        canvas.drawCircle(
            coordinates.x,
            coordinates.y,
            diameter / 2,
            paint
        )
    }

    private fun drawSquare(canvas: Canvas, sideLen: Float) {
        canvas.drawRect(
            getRect(coordinates.x, coordinates.y, sideLen / 2),
            paint
        )
    }

    private fun drawRoundedCornerSquare(canvas: Canvas, sideLen: Float) {
        canvas.drawRoundRect(
            getRect(coordinates.x, coordinates.y, sideLen / 2),
            sideLen / 4,
            sideLen / 4,
            paint
        )
    }

    private fun updateCounter() {
        counterText.text = (++counter).toString()
        if (counter == MAX_FIGURES_VALUE) {
            showToast()
        }
    }

    private fun resetCounter() {
        counter = -1
        updateCounter()
    }

    private fun setOnDrawAfterInvalidate() {
        this.setWillNotDraw(false)
    }

    private fun isMaxFiguresReached() = counter > MAX_FIGURES_VALUE

    private fun setPaintColor() {
        with(colors.size) {
            if (this > 1) {
                paint.color = colors[Random.getRandomValue() % this]
            }
        }
    }

    private fun getRect(x: Float, y: Float, halfSideLen: Float) = RectF(
        x - halfSideLen,
        y - halfSideLen,
        x + halfSideLen,
        y + halfSideLen
    )

    private fun showToast() {
        Toast.makeText(
            context,
            context.getString(R.string.game_over_message),
            Toast.LENGTH_SHORT
        ).show()
    }
}
