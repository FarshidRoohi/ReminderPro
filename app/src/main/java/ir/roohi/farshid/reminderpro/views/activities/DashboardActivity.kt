package ir.roohi.farshid.reminderpro.views.activities

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.service.UserLocationService
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

class DashboardActivity : BaseActivity(), View.OnClickListener {

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

        animatedView(itemReminderLocation, 400)
        animatedView(itemNote, 600)
        animatedView(itemSoundRecorder, 800)

//        startService(Intent(this@DashboardActivity, UserLocationService::class.java))
    }


    private fun animatedView(view: View, time: Long) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_X, valuesForDirection(3000f), 0f).apply {
            duration = time
            start()
        }
    }

    private fun animatedViewGone(view: View, time: Long) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_X, valuesForDirection(100f), valuesForDirection(3000f)).apply {
            duration = time
            start()
            Handler().postDelayed({ animatedView(view, 600) }, 1500)
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
                SettingsActivity.start(this)
            }
        }
    }
}