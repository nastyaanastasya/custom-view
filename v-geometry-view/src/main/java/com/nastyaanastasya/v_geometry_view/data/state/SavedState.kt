package com.nastyaanastasya.v_geometry_view.data.state

import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState

class SavedState : BaseSavedState {
    var counterValue: Int? = null

    constructor(savedState: Parcelable?) : super(savedState)

    constructor(source: Parcel?) : super(source) {
        source?.apply {
            counterValue = readInt()
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        counterValue?.let { out.writeInt(it) }
    }
}
