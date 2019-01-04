package ir.roohi.farshid.reminderpro.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */
@Entity(tableName = "locations")
class LocationEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var date: Date? = null
    var title: String? = null
    var text: String? = null
    var status: Boolean? = null
    var latitude: Double? = null
    var longitude: Double? = null

    @Ignore
    constructor() {

    }

    constructor(id: Int, date: Date, text: String, status: Boolean, latitude: Double, longitude: Double) {
        this.id = id
        this.date = date
        this.text = text
        this.status = status
        this.longitude = longitude
        this.latitude = latitude
    }

    @Ignore
    constructor(date: Date, title: String, text: String, status: Boolean?, latitude: Double, longitude: Double) {
        this.date = date
        this.title = title
        this.text = text
        this.status = status
        this.longitude = longitude
        this.latitude = latitude
    }

    override fun toString(): String {
        return "LocationEntity{" +
                "id=" + id +
                ", title=" + title +
                ", date=" + date +
                ", status=" + status +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", text='" + text + '\''.toString() +
                '}'.toString()
    }
}
