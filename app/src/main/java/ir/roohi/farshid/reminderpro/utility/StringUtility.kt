package ir.roohi.farshid.reminderpro.utility

import java.lang.StringBuilder
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 11/29/18.
 */

fun randomName(len: Int = 20): String {
    val stringBuilder = StringBuilder(len)
    val character = "A1B2C3D4E5F6G7H8I9G0KLMNOP"
    var i = 0
    while (i < len) {
        stringBuilder.append(character[Random().nextInt(character.length)])
        i++
    }


    return stringBuilder.toString()
}