package ir.roohi.farshid.reminderpro.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */
@Entity(tableName = "locations")
class LocationEntity(
    var date: Date,
    var title: String,
    var text: String?,
    var status: Boolean,
    var latitude: Double,
    var longitude: Double,
    var distance: Int
):Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var statusSelect:Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as Date,
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        statusSelect = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeSerializable(date)
        dest.writeString(title)
        dest.writeString(text)
        dest.writeByte(if (status) 1 else 0)
        dest.writeDouble(latitude)
        dest.writeDouble(longitude)
        dest.writeInt(distance)
        dest.writeValue(id)
        dest.writeByte(if (statusSelect) 1 else 0)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<LocationEntity> {
        override fun createFromParcel(parcel: Parcel): LocationEntity {
            return LocationEntity(parcel)
        }

        override fun newArray(size: Int): Array<LocationEntity?> {
            return arrayOfNulls(size)
        }
    }
}