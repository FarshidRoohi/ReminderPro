package ir.roohi.farshid.reminderpro.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */

fun Date.toAgoTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    sdf.timeZone = TimeZone.getTimeZone("GMT")
    val currentTime = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(this.time, currentTime, DateUtils.SECOND_IN_MILLIS).toString()
}