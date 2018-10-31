package ir.farshid_roohi.reminderpro.views.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import ir.farshid_roohi.reminderpro.R
import ir.farshid_roohi.reminderpro.customViews.CustomItemView

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class DashboardActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemNote = findViewById<CustomItemView>(R.id.item_note)
        val itemReminderLocation = findViewById<CustomItemView>(R.id.item_reminder_location)
        val itemSoundRecorder = findViewById<CustomItemView>(R.id.item_sound_recorder)

        var txtHi = findViewById<TextView>(R.id.txt_hi)
        var imgProfile = findViewById<ImageView>(R.id.img_profile)

        itemNote.setOnClickListener(this)
        itemReminderLocation.setOnClickListener(this)
        itemSoundRecorder.setOnClickListener(this)
        imgProfile.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.item_note -> Toast.makeText(this, "note", Toast.LENGTH_SHORT).show()
            R.id.item_reminder_location -> Toast.makeText(this, "reminder location", Toast.LENGTH_SHORT).show()
            R.id.item_sound_recorder -> Toast.makeText(this, "sound recorder", Toast.LENGTH_SHORT).show()
            R.id.img_profile -> Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()


        }
    }
}