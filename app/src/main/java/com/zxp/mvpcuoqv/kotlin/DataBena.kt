package com.zxp.mvpcuoqv.kotlin

import android.os.Parcel
import android.os.Parcelable

data class DataBena(
        val fid: Int,
        val specialty_id: String?,
        val specialty_name: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(fid)
        parcel.writeString(specialty_id)
        parcel.writeString(specialty_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataBena> {
        override fun createFromParcel(parcel: Parcel): DataBena {
            return DataBena(parcel)
        }

        override fun newArray(size: Int): Array<DataBena?> {
            return arrayOfNulls(size)
        }
    }
}