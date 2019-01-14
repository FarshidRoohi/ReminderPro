package ir.roohi.farshid.reminderpro.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */

@Entity(tableName = "voices")
data class VoiceEntity(
    var date: Date,
    var title: String,
    var path: String,
    var duration: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @Ignore var isPlaying: Boolean = false
    @Ignore var statusSelect: Boolean = false
}