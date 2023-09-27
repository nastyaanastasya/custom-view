package com.nastyaanastasya.v_geometry_view.attrs

import android.content.Context
import android.content.res.TypedArray
import com.nastyaanastasya.v_geometry_view.R
import com.nastyaanastasya.v_geometry_view.attrs.GeometryViewAttributes.Companion.DEFAULT_BACKGROUND_COLOR
import com.nastyaanastasya.v_geometry_view.attrs.GeometryViewAttributes.Companion.DEFAULT_GEOMETRY_COLORS

internal object GeometryViewAttributesReader {

    fun read(context: Context, attrs: TypedArray): GeometryViewAttributes = with(attrs) {
        GeometryViewAttributes(
            backgroundColor = getInt(
                R.styleable.GeometryView_gv_background_color,
                DEFAULT_BACKGROUND_COLOR
            ),
            colors = getColorArray(this, getResourceId(R.styleable.GeometryView_gv_colors, -1))
        )
    }

    private fun getColorArray(typedArray: TypedArray, resId: Int): IntArray {
        return when (resId) {
            -1 -> DEFAULT_GEOMETRY_COLORS
            else -> typedArray.resources.getIntArray(resId)
        }
    }
}
