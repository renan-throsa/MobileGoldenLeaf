package com.mithril.mobilegoldenleaf.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore

@Entity
class Clerk : User, Parcelable {
    var email: String? = null
    var password: String? = null


    constructor() {}

    @Ignore
    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        password = parcel.readString()
    }


    @Ignore
    constructor(name: String, phoneNumber: String, email: String, password: String) : super(name, phoneNumber) {
        this.email = email
        this.password = password
    }

    @Ignore
    constructor(id: Long, name: String, phoneNumber: String, email: String, password: String) : super(id, name, phoneNumber) {
        this.email = email
        this.password = password
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clerk> {
        override fun createFromParcel(parcel: Parcel): Clerk {
            return Clerk(parcel)
        }

        override fun newArray(size: Int): Array<Clerk?> {
            return arrayOfNulls(size)
        }
    }


}
