package com.example.sparta_personal4_applemarket.data

import android.os.Parcel
import android.os.Parcelable

data class ListFormat(
    val id: Int,
    val fileName: String,
    val productName: String,
    val productIntro: String,
    val seller: String,
    val price: Int,
    val address: String,
    val likesNum: Int,
    val chatNum: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(fileName)
        parcel.writeString(productName)
        parcel.writeString(productIntro)
        parcel.writeString(seller)
        parcel.writeInt(price)
        parcel.writeString(address)
        parcel.writeInt(likesNum)
        parcel.writeInt(chatNum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListFormat> {
        override fun createFromParcel(parcel: Parcel): ListFormat {
            return ListFormat(parcel)
        }

        override fun newArray(size: Int): Array<ListFormat?> {
            return arrayOfNulls(size)
        }
    }

}