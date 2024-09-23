// Instrument.kt
package com.example.assignment2

import android.os.Parcel
import android.os.Parcelable
// // Declare Instrument data class, implement Parcelable interface to pass data between Activities
data class Instrument(
    var name: String,
    var rating: Float,
    var attributes: List<String>,
    var rentalPrice: Int, // in credits
    val imageResourceId: Int
) : Parcelable {
    var isBooked: Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.readInt(),
        parcel.readInt()
    ) {
        isBooked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(rating)
        parcel.writeStringList(attributes)
        parcel.writeInt(rentalPrice)
        parcel.writeInt(imageResourceId)
        parcel.writeByte(if (isBooked) 1 else 0)
    }

    // Content description, not needed so returns 0
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Instrument> {

        // Create Instrument object from Parcel
        override fun createFromParcel(parcel: Parcel): Instrument {
            return Instrument(parcel)
        }
        // Create an array of Instrument objects
        override fun newArray(size: Int): Array<Instrument?> {
            return arrayOfNulls(size)
        }
    }
}
