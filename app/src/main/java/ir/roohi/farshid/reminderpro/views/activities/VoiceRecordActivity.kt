package ir.roohi.farshid.reminderpro.views.activities

import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.utility.getVoiceDirectory
import ir.roohi.farshid.reminderpro.utility.initialize
import ir.roohi.farshid.reminderpro.utility.showMsg
import ir.roohi.farshid.reminderpro.utility.toHumanTime
import kotlinx.android.synthetic.main.activity_voice_record.*
import java.io.IOException


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 4/5/20.
 */
class VoiceRecordActivity : BaseActivity() {

    private val mediaRecorder = MediaRecorder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_record)


        txtChronometer.format = "H:MM:SS"
        txtChronometer.setText(R.string.zero_time)
        txtChronometer.setOnChronometerTickListener {
            val time: Long = SystemClock.elapsedRealtime() - txtChronometer.base
            txtChronometer.text = time.toHumanTime()
        }
        fabDelete.isEnabled = false
        fabSave.isEnabled = false

        fabDelete.setOnClickListener {
            lottieLayer.cancelAnimation()
            mediaRecorder.stop()
            mediaRecorder.reset()
        }
        fabRecord.setOnClickListener {

            if (lottieLayer.isAnimating) {
                mediaRecorder.stop()
                mediaRecorder.reset()
                txtChronometer.stop()
                fabRecord.setImageResource(R.drawable.ic_microphone)
                lottieLayer.cancelAnimation()
                showMsg(R.string.stop_recording)
                return@setOnClickListener
            }

            lottieLayer.playAnimation()
            fabRecord.setImageResource(R.drawable.ic_stop)

            try {
                this.mediaRecorder.initialize()
                mediaRecorder.setOutputFile(getVoiceDirectory())
                mediaRecorder.prepare()
                mediaRecorder.start()
                txtChronometer.start()
                showMsg(R.string.start_recording)
            } catch (e: IOException) {
                e.printStackTrace()
                showMsg(getString(R.string.error_unknown))
                finish()
            }
        }
        fabSave.setOnClickListener {
            lottieLayer.cancelAnimation()
            mediaRecorder.release()
        }

    }

}