package ir.roohi.farshid.reminderpro.utility

import android.content.Context
import ir.roohi.farshid.reminderpro.R
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/24/19.
 */
class EPrettyTime(val context: Context) : PrettyTime() {

    init {
        this.locale = Locale.ENGLISH
    }

    fun getPrettyTimeFormat(date: Date): String {
       return format(date)
    }

    override fun format(then: Date?): String {
        val result = super.format(then)
        try {

            val digit = Integer.parseInt(result.replace("[^\\d]".toRegex(), "").trim { it <= ' ' })

            if (result.contains("second")) {
                return context.getString(R.string.just_now)
            }

            if (result.contains("minute")) {
                return getDateValue(context.getString(R.string.min_ago), digit)
            }

            if (result.contains("hour")) {
                return getDateValue(context.getString(R.string.hour_ago), digit)
            }

            if (result.contains("day")) {
                return getDateValue(context.getString(R.string.day_ago), digit)
            }

            if (result.contains("week")) {
                return getDateValue(context.getString(R.string.week_ago), digit)
            }

            if (result.contains("month")) {
                return getDateValue(context.getString(R.string.month_ago), digit)
            }

            return if (result.contains("year")) {
                getDateValue(context.getString(R.string.year_ago), digit)
            } else context.getString(R.string.just_now)

        } catch (ex: Exception) {
            return context.getString(R.string.just_now)
        }

    }

    private fun getDateValue(value: String, number: Int): String {
        return String.format("%s %s", number, value)
    }

}