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
data class LocationEntity(
    var date: Date,
    var title: String,
    var text: String,
    var status: Boolean,
    var latitude: Double,
    var longitude: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}