package ir.roohi.farshid.reminderpro.database


import androidx.room.TypeConverter

import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class DateConverter {

    @TypeConverter
    fun toDate(time: Long?): Date? {
        return if (time == null) null else Date(time)
    }

    @TypeConverter
    fun toTimesTemp(date: Date?): Long? {
        return date?.time
    }
}
