package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.utility.randomName
import kotlinx.android.synthetic.main.activity_record_sound.*
import java.io.File
import java.io.IOException


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class RecordSoundActivity : BaseActivity(), View.OnClickListener, BaseActivity.OnPermissionRequestListener {

    private var flagRecording = false
    private lateinit var recorder: MediaRecorder
    private lateinit var filePath: String


    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RecordSoundActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_sound, R.color.color_gradient_dark_two)

        layoutRecordState.setOnClickListener(this)
        imgBack.setOnClickListener(this)
        imgShare.setOnClickListener(this)

        requestPermission(permissions, this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layoutRecordState -> {
                handledVoiceRecording()
                this.flagRecording = !this.flagRecording
            }
            R.id.imgBack -> finish()
            R.id.imgShare -> {
                customProgressCircle.stopAnimated()
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun handledVoiceRecording() {
        if (this.flagRecording) {
            this.txtTitleStatus.text = getString(R.string.start_recording)
            this.imgStatus.setImageResource(R.drawable.ic_play)
            this.customProgressCircle.stopAnimated()


        } else {
            this.txtTitleStatus.text = getString(R.string.stop_recording)
            this.imgStatus.setImageResource(R.drawable.ic_stop)
            this.customProgressCircle.startAnimated()

        }
    }

    private fun prepare() {
        this.filePath = String.format("%s%s%s", resourceApp!!.getDirSoundSave(), randomName(), ".3gp")
        val file = File(filePath)
        if (!file.mkdirs()) {
            showMsg(getString(R.string.error_can_not_create_file))
            return
        }

        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
        recorder.setAudioSamplingRate(44100)
        recorder.setAudioEncodingBitRate(16)
        recorder.setOutputFile(filePath)
        try {
            recorder.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    override fun onAllow(permission: String) {
        if (checkPermissions(permissions)) {
            prepare()
        }
    }

    override fun onDenied(permission: String) {
        val alertBuilder = AlertDialog.Builder(supportFragmentManager, getString(R.string.permission), getString(R.string.permission_audio))
        alertBuilder.setBtnPositive(getString(R.string.yes)) {
            requestPermission(permissions, this)
        }
        alertBuilder.setBtnNegative(getString(R.string.no)) {
            finish()
        }
        alertBuilder.build().show()
    }


}