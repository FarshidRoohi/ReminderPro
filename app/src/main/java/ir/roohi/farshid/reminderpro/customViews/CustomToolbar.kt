package ir.roohi.farshid.reminderpro.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.toolbar.view.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */
class CustomToolbar : Toolbar {

    private var title: String? = ""
    private var leftIcon: Int = 0
    private var rightIcon: Int = 0
    private var tint: Int = 0

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }


    private fun initialize(attrs: AttributeSet? = null) {

        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true)

        this.setContentInsetsAbsolute(0, 0)

        attrs?.let {
            val arrayType = context.obtainStyledAttributes(it, R.styleable.CustomToolbar)

            this.leftIcon = arrayType.getResourceId(R.styleable.CustomToolbar_leftImage, R.drawable.ic_arrow_back)
            this.rightIcon = arrayType.getResourceId(R.styleable.CustomToolbar_rightImage, 0)
            this.title = arrayType.getString(R.styleable.CustomToolbar_caption)
            this.tint = arrayType.getColor(
                R.styleable.CustomToolbar_elementTint,
                ContextCompat.getColor(context, R.color.color_tint_toolbar)
            )

            arrayType.recycle()
        }

        this.title?.let {
            txtTitle.text = it
        }
        this.imgLeft.setImageResource(this.leftIcon)
        this.imgRight.setImageResource(this.rightIcon)
        this.txtTitle.setTextColor(this.tint)
        this.imgRight.setColorFilter(this.tint)
        this.imgLeft.setColorFilter(this.tint)

        val direction = resources.configuration.layoutDirection
        imgLeft.rotation = if (direction == 0) 0f else 180f

    }

    fun setIconLeftListener(listener: OnClickListener) {
        this.imgLeft.setOnClickListener(listener)
    }

    fun setIconRightListener(listener: OnClickListener) {
        this.imgRight.setOnClickListener(listener)
    }

    fun setCaption(title: String) {
        this.txtTitle.text = title
    }

    fun getLeftImageView(): ImageView = this.imgLeft
    fun getRightImageView(): ImageView = this.imgRight
    fun getTxtTitle():TextView = this.txtTitle

    fun rotateLayoutDirection() {
        val direction = resources.configuration.layoutDirection
        imgLeft.rotation = if (direction == 0) 0f else 180f

        if (resources.configuration.layoutDirection == 0) {
            layoutDirection = ViewCompat.LAYOUT_DIRECTION_LTR
        } else {
            layoutDirection = ViewCompat.LAYOUT_DIRECTION_RTL

        }
    }
}