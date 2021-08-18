package ir.roohi.farshid.reminderpro.utility

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import ir.roohi.farshid.reminderpro.R
import java.io.File
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */

fun Date.toPrettyTime(context: Context): String {
    return EPrettyTime(context).getPrettyTimeFormat(this)
}

fun View.animatedColorBackgroundSelected(isSelected: Boolean = true) {
    val colorFrom: Int
    val colorTo: Int

    if (!isSelected) {
        colorFrom = ContextCompat.getColor(
            context, R.color.color_background_select_item_recycler_view
        )
        colorTo = ContextCompat.getColor(context, R.color.color1)
    } else {
        colorFrom = ContextCompat.getColor(context, R.color.color1)
        colorTo =
            ContextCompat.getColor(context, R.color.color_background_select_item_recycler_view)
    }

    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = 250 // milliseconds
    colorAnimation.addUpdateListener { animator -> this.setBackgroundColor(animator.animatedValue as Int) }
    colorAnimation.start()
}


// String extension
fun String.share(context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, this)
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_text)))
}

// String extension
fun shareVoice(context: Context, path: String) {
    val share = Intent(Intent.ACTION_SEND)
    share.type = "audio/*"
    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(path)))
    context.startActivity(Intent.createChooser(share, "Share Sound File"))

}


fun Context.toast(msg: String) {
    Toast.makeText(this, msg.trim(), Toast.LENGTH_LONG).show()
}

fun Context.toast(@StringRes msg: Int) {
    toast(getString(msg))
}

public fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}


private fun capitalize(s: String?): String {

    if (s.isNullOrEmpty()) {
        return ""
    }

    val first = s[0]
    return if (Character.isUpperCase(first)) {
        s
    } else {
        Character.toUpperCase(first) + s.substring(1)
    }
}

fun Context.voiceDIR(): String {
    return "${pathDir()}/record"
}

fun Context.pathDir(): String {
    val dirApp = Environment.getExternalStorageDirectory().absolutePath
    return "$dirApp/android/data/$packageName"
}

fun MediaRecorder.initialize(): MediaRecorder {
    setAudioSource(MediaRecorder.AudioSource.MIC)
    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
    setAudioEncodingBitRate(320)
    return this
}