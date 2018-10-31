package ir.farshid_roohi.reminderpro.views.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import ir.farshid_roohi.reminderpro.R
import ir.farshid_roohi.reminderpro.customViews.CustomItemView

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

class DashboardActivity : BaseActivity(), View.OnClickListener {

    lateinit var itemReminderLocation: CustomItemView
    lateinit var itemNote: CustomItemView
    lateinit var itemSoundRecorder: CustomItemView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemReminderLocation = findViewById(R.id.item_reminder_location)
        itemNote = findViewById(R.id.item_note)
        itemSoundRecorder = findViewById(R.id.item_sound_recorder)

//        val txtHi = findViewById<TextView>(R.id.txt_hi)
        val imgProfile = findViewById<ImageView>(R.id.img_profile)

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
            R.id.item_note -> {
                animatedViewGone(itemNote, 600)
                ListNoteActivity.start(this)
            }
            R.id.item_reminder_location -> {
                animatedViewGone(itemReminderLocation, 400)
                ReminderLocationActivity.start(this)
            }
            R.id.item_sound_recorder -> {
                animatedViewGone(itemSoundRecorder, 800)
               SoundRecorderActivity.start(this)
            }
            R.id.img_profile -> {
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