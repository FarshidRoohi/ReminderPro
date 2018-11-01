package ir.roohi.farshid.reminderpro.database;


import android.arch.persistence.room.TypeConverter;

import java.util.*;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long time) {
        return time == null ? null : new Date(time);
    }

    @TypeConverter
    public static Long toTimesTemp(Date date) {
        return date == null ? null : date.getTime();
    }
}
