package ir.roohi.farshid.reminderpro.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView
import java.util.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/20/19.
 */
class CustomCircleAnimation : ImageView {

    private var circleList = ArrayList<Circle>()

    private lateinit var thread: Thread

    private val strokeWidth = 6
    private val circleLifeTime = 4000


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        add()
        update()
    }

    private fun getRectF(size: Float): RectF {
        val rectF = RectF()
        val arcCenterX = width.toFloat() / 2
        val arcCenterY = height.toFloat()
        rectF.set(arcCenterX - size, arcCenterY - size, arcCenterX + size, arcCenterY + size)
        return rectF
    }

    private fun getPaint(): Paint {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.isAntiAlias = true
        paint.strokeWidth = strokeWidth.toFloat()
        paint.style = Paint.Style.STROKE
        return paint
    }

    fun update() {
        val arcCenterX = width.toFloat() / 2
        val arcCenterY = height.toFloat()

        thread = Thread(Runnable {

            while (true) {
                Thread.sleep(30)

                circleList.forEach {
                    it.arcSize += 5
                    it.rectF.set(
                        arcCenterX - it.arcSize,
                        arcCenterY - it.arcSize,
                        arcCenterX + it.arcSize,
                        arcCenterY + it.arcSize
                    )
                }
                postInvalidate()
            }
        })
        thread.start()
    }

    fun add() {
        val addThread = Thread(Runnable {
            while (true) {
//                val sleepTime = (Random().nextInt((3500 - 800) + 1) + 800).toLong()
                Thread.sleep(2000)

                val iterator = circleList.iterator()

                while (iterator.hasNext()){
                    val it = iterator.next()
                    if ((System.currentTimeMillis() - it.timeCircle) > circleLifeTime) {
                        iterator.remove()
                    }
                }
                circleList.add(Circle(getRectF(1f), 15f, System.currentTimeMillis(), getPaint()))
                postInvalidate()
            }
        })
        addThread.start()

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (circleList.isEmpty()){
            return
        }
        circleList.forEach {
            val elapsed: Long = System.currentTimeMillis() - it.timeCircle
            val alpha = 255 - (elapsed / 15.68).toInt()
            it.paint.alpha = alpha
            canvas!!.drawArc(it.rectF, 180f, 180f, true, it.paint)
        }
    }

    private inner class Circle(var rectF: RectF, var arcSize: Float, val timeCircle: Long, var paint: Paint)
}