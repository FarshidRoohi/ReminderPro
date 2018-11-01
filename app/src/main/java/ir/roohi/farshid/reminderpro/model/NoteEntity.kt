package ir.roohi.farshid.reminderpro.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

import java.util.Date

@Entity(tableName = "notes")
class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var date: Date? = null
    var title: String? = null
    var text: String? = null


    @Ignore
    constructor() {

    }

    constructor(id: Int, date: Date, text: String) {
        this.id = id
        this.date = date
        this.text = text
    }

    @Ignore
    constructor(date: Date, title: String, text: String) {
        this.date = date
        this.title = title
        this.text = text
    }

    override fun toString(): String {
        return "NoteEntity{" +
                "id=" + id +
                ", title=" + title +
                ", date=" + date +
                ", text='" + text + '\''.toString() +
                '}'.toString()
    }
}
