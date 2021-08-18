package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.listener.OnPermissionRequestListener
import ir.roohi.farshid.reminderpro.utility.*
import ir.roohi.farshid.reminderpro.viewModel.VoiceViewModel
import ir.roohi.farshid.reminderpro.views.bottomSheet.NameBottomSheet
import kotlinx.android.synthetic.main.activity_voice_record.*
import java.io.File
import java.io.IOException
import java.sql.Time


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 4/5/20.
 */
class VoiceRecordActivity : BaseActivity() {

    private val mediaRecorder = MediaRecorder()
    private var voiceDIR: String = ""

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
            lottieLayer.clearAnimation()
        }

        fabRecord.setOnClickListener {

            if (lottieLayer.isAnimating) {
                mediaRecorder.stop()
                mediaRecorder.reset()
                txtChronometer.stop()
                fabRecord.setImageResource(R.drawable.ic_microphone)
                lottieLayer.cancelAnimation()
                toast(R.string.stop_recording)
                fabDelete.isEnabled = true
                fabSave.isEnabled = true
                return@setOnClickListener
            }

            lottieLayer.playAnimation()
            fabRecord.setImageResource(R.drawable.ic_stop)

            try {
                val dir = this.voiceDIR()
                File(dir)
                        .mkdirs()
                this.voiceDIR = "$dir/voice-${randomName()}.3gp"
                this.mediaRecorder.initialize()
                mediaRecorder.setOutputFile(this.voiceDIR)
                mediaRecorder.prepare()
                mediaRecorder.start()
                txtChronometer.base = SystemClock.elapsedRealtime()
                txtChronometer.start()
                toast(R.string.start_recording)
                fabDelete.isEnabled = false
            } catch (e: IOException) {
                e.printStackTrace()
                toast(getString(R.string.error_unknown))
                finish()
            }
        }
        fabSave.setOnClickListener {
            lottieLayer.cancelAnimation()
            mediaRecorder.release()

            val bottomSheet = NameBottomSheet(supportFragmentManager)
            bottomSheet.listener = object : NameBottomSheet.OnTitleListener {
                override fun onTitle(title: String) {
                    toast(getString(R.string.save))

                    val player = MediaPlayer()
                    player.setDataSource(voiceDIR)
                    player.prepare()
                    val viewModel = ViewModelProviders.of(this@VoiceRecordActivity).get(VoiceViewModel::class.java)
                    viewModel.add(title, voiceDIR, player.duration)
                    finish()
                }
            }
            bottomSheet.show()
        }

        requestPermission(arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), object : OnPermissionRequestListener {
            override fun onAllow(permission: String) {
            }

            override fun onDenied(permission: String) {
                toast(R.string.permission_audio)
                finish()
            }

        })

    }


}