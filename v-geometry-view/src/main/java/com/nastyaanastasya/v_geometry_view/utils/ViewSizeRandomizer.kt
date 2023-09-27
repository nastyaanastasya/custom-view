package com.nastyaanastasya.v_geometry_view.utils

import kotlin.math.abs
import kotlin.random.Random

private const val MIN_VIEW_SIZE = 100
private const val MAX_VIEW_SIZE = 250

object ViewSizeRandomizer {
    fun getRandomSize() = Random.nextInt(MIN_VIEW_SIZE, MAX_VIEW_SIZE).toFloat()
    fun getRandomValue() = abs(Random.nextInt())
}
