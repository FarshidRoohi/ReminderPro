package ir.roohi.farshid.reminderpro.views.activities

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

class DashboardActivity : BaseActivity(), View.OnClickListener {


    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemNote.setOnClickListener(this)
        itemReminderLocation.setOnClickListener(this)
        itemSoundRecorder.setOnClickListener(this)
        imgSettings.setOnClickListener(this)
        layoutRating.setOnClickListener(this)
        layoutFeedback.setOnClickListener(this)
        layoutPeek.setOnClickListener(this)

        animatedView(itemReminderLocation, 400)
        animatedView(itemNote, 600)
        animatedView(itemSoundRecorder, 800)

        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)



        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                imgArrowBottomSheet.rotation = (slideOffset * 180)
                updateRadius((slideOffset * 180))
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

        })

    }

    private fun updateRadius(value: Float) {
        if (value > 70) {
            return
        }
        val gradient = GradientDrawable()
        gradient.setColor(ContextCompat.getColor(this, R.color.color_background))
        gradient.cornerRadii = floatArrayOf(value, value, value, value, 0.0f, 0.0f, 0.0f, 0.0f)
        layoutPeek.background = gradient
    }


    private fun animatedView(view: View, time: Long) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_X, valuesForDirection(3000f), 0f).apply {
            duration = time
            start()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.itemNote -> {
                NoteListActivity.start(this)
            }
            R.id.itemReminderLocation -> {
                LocationListActivity.start(this)
            }
            R.id.itemSoundRecorder -> {
                SoundListActivity.start(this)
            }
            R.id.imgSettings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.layoutRating -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                closeBottomSheet()
            }
            R.id.layoutSettings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.layoutFeedback -> {
                startActivity(Intent(this, FeedbackActivity::class.java))
                closeBottomSheet()
            }
            R.id.layoutPeek -> {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    private fun closeBottomSheet() {
        Handler().postDelayed({ bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED }, 1000)
    }
}