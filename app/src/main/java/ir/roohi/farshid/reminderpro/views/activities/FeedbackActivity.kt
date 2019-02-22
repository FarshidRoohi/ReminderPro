package ir.roohi.farshid.reminderpro.views.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.viewModel.FeedbackViewModel
import kotlinx.android.synthetic.main.feedback_activity.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
class FeedbackActivity : BaseActivity() {

    lateinit var viewModel: FeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback_activity)

        this.viewModel = ViewModelProviders.of(this).get(FeedbackViewModel::class.java)
        this.viewModel.mutableLiveData.observe(this, Observer<Boolean> {
            layoutSend.isEnabled = true
            edtFeedback.isEnabled = true
            progressBar.visibility = View.GONE
            if (it) {
                showMsg(getString(R.string.success_send_feedback))
                finish()
            } else {
                showMsg(getString(R.string.failure_send_feedback))
            }
        })

        toolbar.getLeftImageView().setOnClickListener { finish() }
        layoutSend.setOnClickListener { request() }

    }

    private fun request() {
        progressBar.visibility = View.VISIBLE
        layoutSend.isEnabled = false
        edtFeedback.isEnabled = false
        this.viewModel.send(edtFeedback.text.toString(), "farshid roohi")


    }
}