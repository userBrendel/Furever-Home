// USE TO GET DATA FROM CATS AND DOG ACTIVITY
package com.example.fureverhome

//imports helps to pass the entire Pet object between activities efficiently without having to pass each individually
import android.os.Parcel
import android.os.Parcelable

// Defining the Pet data class with properties representing the attributes of a pet
// use to store list
data class Pet(
    val name: String,
    val age: Int,
    val location: String,
    val breed: String,
    val imageUrl: String,
    val details: String,
    val extraDetails: String
) : Parcelable {
    // Secondary constructor used for parcelable implementation
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Function to write object values to parcel // when adding pet
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(location)
        parcel.writeString(breed)
        parcel.writeString(imageUrl)
        parcel.writeString(details)
        parcel.writeString(extraDetails)
    }

    override fun describeContents(): Int = 0

    // Companion object implementing the Parcelable.Creator interface
    companion object CREATOR : Parcelable.Creator<Pet> {
        // Function to create an instance of Pet from a Parcel
        override fun createFromParcel(parcel: Parcel): Pet = Pet(parcel)
        //Make new parcel with position/size
        override fun newArray(size: Int): Array<Pet?> = arrayOfNulls(size)
    }
}

