package ir.roohi.farshid.reminderpro.views.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.FeedbackActivityBinding
import ir.roohi.farshid.reminderpro.utility.toast
import ir.roohi.farshid.reminderpro.viewModel.FeedbackViewModel
import kotlinx.android.synthetic.main.feedback_activity.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
class FeedbackActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feedbackActivityBinding = DataBindingUtil.setContentView<FeedbackActivityBinding>(
            this,
            R.layout.feedback_activity
        )

        val viewModel = ViewModelProviders.of(this).get(FeedbackViewModel::class.java)
        feedbackActivityBinding.viewModel = viewModel

        btnSend.setOnClickListener {
            val name = edtName.text.toString()
            val message = edtFeedback.text.toString()

            if (name.isEmpty()) {
                toast(R.string.error_empty_name)
                return@setOnClickListener
            }
            if (message.isEmpty() || message.length < 5) {
                toast(R.string.error_empty_content)
                return@setOnClickListener
            }

            viewModel.send(name, message).observe(this, Observer {
                if (it) {
                    toast(getString(R.string.success_send_feedback))
                    finish()
                } else {
                    toast(getString(R.string.failure_send_feedback))
                }
            })
        }


        toolbar.getLeftImageView().setOnClickListener { finish() }


    }
}