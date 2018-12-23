package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_sound_list.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */

class SoundListActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SoundListActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_list)

        imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> finish()
            R.id.fabAdd -> RecordSoundActivity.start(this)
        }
    }

}