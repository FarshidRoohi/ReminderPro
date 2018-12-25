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
class VoiceEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var date: Date? = null
    var title: String? = null
    var path: String? = null

    @Ignore
    constructor(date: Date?, title: String?, path: String?) {
        this.date = date
        this.title = title
        this.path = path
    }

    constructor() {
    }

    constructor(id: Int, date: Date?, title: String?, path: String?) {
        this.id = id
        this.date = date
        this.title = title
        this.path = path
    }


    override fun toString(): String {
        return "VoiceEntity{" +
                "id=" + id +
                ", title=" + title +
                ", date=" + date +
                ", text='" + path + '\''.toString() +
                '}'.toString()
    }
}