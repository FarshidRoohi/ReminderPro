package ir.roohi.farshid.reminderpro.views.activities

import android.os.Bundle
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.feedback_activity.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
class FeedbackActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback_activity)

        toolbar.getLeftImageView().setOnClickListener { finish() }
        layoutSend.setOnClickListener { request() }

    }

   private fun request(){
       layoutSend.isEnabled = false

    }
}