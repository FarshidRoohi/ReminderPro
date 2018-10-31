package ir.farshid_roohi.reminderpro.customViews;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by FarshidRoohi.
 * MyZarinPal-Android-V4 | Copyrights (c) 2018.
 */

public class ViewUtility {

    public static float dpToPx(Context context, int pixel) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, r.getDisplayMetrics());
    }

    public static float pxToDp(Context context, float px) {
        Resources      resources = context.getResources();
        DisplayMetrics metrics   = resources.getDisplayMetrics();
        float          dp        = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
