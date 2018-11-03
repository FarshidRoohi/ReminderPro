package ir.roohi.farshid.reminderpro.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import ir.roohi.farshid.reminderpro.R

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 11/4/18.
 */
class CustomProgressCircle : RelativeLayout {

    private var progressBar: ProgressBar? = null
    private var imgIcon: ImageView? = null
    private var thread: Thread? = null


    private var progress = 0
    private var progressMax = 100
    private var iconReferenced = 0

    private lateinit var pulseAnimation: Animation


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {

        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressCircle)

            this.iconReferenced = typeArray.getResourceId(R.styleable.CustomProgressCircle_android_src, 0)
            this.progressMax = typeArray.getInt(R.styleable.CustomProgressCircle_android_max, 100)
            this.progress = typeArray.getResourceId(R.styleable.CustomProgressCircle_android_src, 65)


            typeArray.recycle()
        }


        val view = LayoutInflater.from(context).inflate(R.layout.custom_progress_circle, this, true)

        this.progressBar = view.findViewById(R.id.progress_bar)
        this.imgIcon = view.findViewById(R.id.img_icon)

        if (this.iconReferenced != 0) {
            this.imgIcon!!.setImageResource(this.iconReferenced)
        }

        this.progressBar!!.max = this.progressMax
        this.progressBar!!.progress = this.progress

        pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)

    }

    fun setProgress(progress: Int) {
        this.progress = progress
        this.progressBar!!.progress = progress
    }

    fun setProgressMax(max: Int) {
        this.progressMax = max
        this.progressBar!!.max = max
    }


    fun startAnimated() {
        this.thread = getThreadProgressBar()
        this.thread!!.start()
        imgIcon!!.startAnimation(pulseAnimation)
    }

    fun stopAnimated() {
        if (this.thread != null) {
            this.thread!!.interrupt()
        }
        this.progressBar!!.progress = 0
        this.imgIcon!!.clearAnimation()
    }

    private fun getThreadProgressBar(): Thread {
        return Thread(Runnable {
            var number = 0

            while (true) {

                try {
                    if (Thread.interrupted()) throw InterruptedException()

                    if (number == 1000) number = 0
                    number += 20
                    progressBar!!.progress = number
                    Thread.sleep(30)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    return@Runnable
                }


            }
        })
    }


}
