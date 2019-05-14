package ir.roohi.farshid.reminderpro.utility

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.ResourceApplication
import ir.roohi.farshid.reminderpro.utility.EPrettyTime
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */

fun Date.toAgoTime(context: Context): String {
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
        colorTo = ContextCompat.getColor(context, R.color.color_background_select_item_recycler_view)
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