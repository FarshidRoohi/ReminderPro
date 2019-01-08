package ir.roohi.farshid.reminderpro.views.activities

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import ir.roohi.farshid.reminderpro.R
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
        ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 100f, 3000f).apply {
            duration = time
            start()
            Handler().postDelayed({ animatedView(view, 600)}, 1500)
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.itemNote -> {
                animatedViewGone(itemNote, 600)

                Handler().postDelayed({
                    NoteListActivity.start(this)
                }, 350)
            }
            R.id.itemReminderLocation -> {
                animatedViewGone(itemReminderLocation, 400)
                Handler().postDelayed({
                    LocationListActivity.start(this)
                }, 350)

            }
            R.id.itemSoundRecorder -> {
                animatedViewGone(itemSoundRecorder, 800)

                Handler().postDelayed({
                    SoundListActivity.start(this)
                }, 450)
            }
            R.id.imgProfile -> {
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
            }

        }
    }

}