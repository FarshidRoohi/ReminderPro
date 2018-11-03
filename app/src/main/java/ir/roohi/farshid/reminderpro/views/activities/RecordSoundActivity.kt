package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.zarinpal.libs.views.ZarinTimer
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_record_sound.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class RecordSoundActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RecordSoundActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_sound)

        layoutRecordState.setOnClickListener(this)

        Thread(Runnable {
            var number = 0
            while (true) {
                if (number == 1000) number = 0
                number += 20
                progressBar.progress = number
                Thread.sleep(30)

            }
        }).start()


        val pulse = AnimationUtils.loadAnimation(this, R.anim.pulse)
        imgVoice.startAnimation(pulse)

    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.layoutRecordState) {

        }
    }


}