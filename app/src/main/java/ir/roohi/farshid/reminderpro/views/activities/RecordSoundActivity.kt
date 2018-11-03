package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        imgBack.setOnClickListener(this)
        imgShare.setOnClickListener(this)

        customProgressCircle.startAnimated()


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layoutRecordState -> {

            }
            R.id.imgBack -> finish()
            R.id.imgShare -> {
                customProgressCircle.stopAnimated()
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()
            }
        }
    }


}