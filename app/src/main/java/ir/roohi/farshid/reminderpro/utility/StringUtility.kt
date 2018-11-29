package ir.roohi.farshid.reminderpro.utility

import java.lang.StringBuilder
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 11/29/18.
 */

fun randomName(len: Int = 6): String {
    val stringBuilder = StringBuilder(len)
    val character = "A1-B2C3-D4E-5F6-G7H-8I9G0KL1-M-3N-OP"
    var i = 0
    while (i < len) {
        stringBuilder.append(character[Random().nextInt(character.length)])
        i++
    }


    return stringBuilder.toString()
}