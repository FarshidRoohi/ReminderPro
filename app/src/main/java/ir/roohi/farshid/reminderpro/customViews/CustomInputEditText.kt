package ir.roohi.farshid.reminderpro.customViews


import android.content.Context
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.util.AttributeSet

import androidx.annotation.IntDef
import com.airbnb.paris.Paris
import ir.roohi.farshid.reminderpro.R

/**
 * Created by FarshidRoohi.
 * MyZarinPal-Android-V4 | Copyrights (c) 2018.
 */


class CustomInputEditText : TextInputLayout {

    var edt: TextInputEditText? = null
        private set
    private var hint: String? = null
    private var styleEdt: Int = 0

    var text: String
        get() = if (this.edt == null) {
            ""
        } else this.edt!!.text!!.toString()
        set(text) {
            if (this.edt == null) {
                return
            }

            this.edt!!.setText(text)
        }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initAttr(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.initAttr(attrs)
    }

    private fun initAttr(attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomInputEditText)

        try {

            this.hint = typedArray.getString(R.styleable.CustomInputEditText_android_hint)
            this.styleEdt = typedArray.getResourceId(R.styleable.CustomInputEditText_zv_style, 0)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        typedArray.recycle()

        this.initView()

    }

    private fun initView() {

        this.setHint(this.hint)
        this.edt = TextInputEditText(context)
        this.addView(this.edt)
        Paris.styleBuilder(this.edt).add(styleEdt).apply()

    }

    fun addPrefix(prefix: String) {
        val result = String.format("%s %s", text, prefix)
        text = result
    }

    fun addSuffix(suffix: String) {
        val result = String.format("%s %s", suffix, text)
        text = result
    }

    object ZVTextViewFormat {

        const val NORMAL = 0
        const val PAN = 1
        const val CURRENCY = 2
        const val PRETTY_TIME = 3

        @IntDef(NORMAL, PAN, CURRENCY, PRETTY_TIME)
        annotation class TextViewFormat

    }

}
