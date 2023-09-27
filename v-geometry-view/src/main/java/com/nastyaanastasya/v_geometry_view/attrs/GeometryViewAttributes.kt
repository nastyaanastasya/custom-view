package com.nastyaanastasya.v_geometry_view.attrs

import android.graphics.Color

data class GeometryViewAttributes(
    val backgroundColor: Int,
    val colors: IntArray,
) {
    companion object {
        const val DEFAULT_BACKGROUND_COLOR = Color.GREEN

        val DEFAULT_GEOMETRY_COLORS = intArrayOf(Color.BLUE)
    }
}
