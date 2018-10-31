package ir.farshid_roohi.reminderpro.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.farshid_roohi.reminderpro.R;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */


public class CustomItemView extends LinearLayout {


    public CustomItemView(Context context) {
        super(context);
    }

    public CustomItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initAttr(context, attrs);
    }

    public CustomItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initAttr(context, attrs);
    }

    private void initAttr(Context context, @Nullable AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomItemView);

        int    backgroundColor  = typedArray.getResourceId(R.styleable.CustomItemView_android_background, Color.WHITE);
        int    icon             = typedArray.getResourceId(R.styleable.CustomItemView_item_icon, 0);
        int    backgroundRadius = (int) typedArray.getDimension(R.styleable.CustomItemView_item_radius, 10);
        int    tintColor        = typedArray.getResourceId(R.styleable.CustomItemView_android_tint, Color.DKGRAY);
        String text             = typedArray.getString(R.styleable.CustomItemView_android_text);


        typedArray.recycle();


        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_view, this, true);

        TextView  txtCaption = view.findViewById(R.id.txt_caption);
        ImageView imgIcon    = view.findViewById(R.id.img_icon);

        if (icon != 0) {
            imgIcon.setImageResource(icon);
            imgIcon.setColorFilter(ContextCompat.getColor(context, tintColor));
        }
        if (text != null) {
            txtCaption.setText(text);
            txtCaption.setTextColor(ContextCompat.getColor(context, tintColor));
        }


        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ContextCompat.getColor(context, backgroundColor));
        float[] radii = {backgroundRadius, backgroundRadius, 0,
                0, 0, 0, backgroundRadius, backgroundRadius};
        gradientDrawable.setCornerRadii(radii);

        this.setBackground(gradientDrawable);


    }


}
