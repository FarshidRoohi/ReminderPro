package ir.roohi.farshid.reminderpro.views.activities

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

class DashboardActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        itemNote.setOnClickListener(this)
        itemReminderLocation.setOnClickListener(this)
        itemSoundRecorder.setOnClickListener(this)
        imgProfile.setOnClickListener(this)


        animatedView(itemReminderLocation, 400)
        animatedView(itemNote, 600)
        animatedView(itemSoundRecorder, 800)

    }


    private fun animatedView(view: View, time: Long) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 3000f, 0f).apply {
            duration = time
            start()
        }
    }

    private fun animatedViewGone(view: View, time: Long) {
        Thread().run {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 100f, 3000f).apply {
                duration = time
                start()
            }
        }.start()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.itemNote -> {
                animatedViewGone(itemNote, 600)
                ListNoteActivity.start(this)
            }
            R.id.itemReminderLocation -> {
                animatedViewGone(itemReminderLocation, 400)
                ReminderLocationActivity.start(this)
            }
            R.id.itemSoundRecorder -> {
                animatedViewGone(itemSoundRecorder, 800)
                SoundRecorderActivity.start(this)
            }
            R.id.imgProfile -> {
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        animatedView(itemReminderLocation, 400)
        animatedView(itemNote, 600)
        animatedView(itemSoundRecorder, 800)
    }

}