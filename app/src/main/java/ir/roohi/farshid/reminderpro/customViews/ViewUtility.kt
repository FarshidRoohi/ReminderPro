package ir.roohi.farshid.reminderpro.customViews


import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by FarshidRoohi.
 * MyZarinPal-Android-V4 | Copyrights (c) 2018.
 */

object ViewUtility {

    fun dpToPx(context: Context, pixel: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel.toFloat(), r.displayMetrics)
    }

    fun pxToDp(context: Context, px: Float): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
