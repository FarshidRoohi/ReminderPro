package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.utility.convertToTime
import ir.roohi.farshid.reminderpro.utility.formatFileSize
import ir.roohi.farshid.reminderpro.utility.randomName
import kotlinx.android.synthetic.main.activity_record_sound.*
import java.io.File
import java.io.IOException
import java.lang.Exception


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class RecordSoundActivity : BaseActivity(), View.OnClickListener, BaseActivity.OnPermissionRequestListener {

    private var oncePlay = true
    private lateinit var recorder: MediaRecorder
    private var player = MediaPlayer()
    private lateinit var filePath: String
    private var status: Status = Status.RECORD


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


        this.player = MediaPlayer()


        fabRecord.setOnClickListener(this)
        imgBack.setOnClickListener(this)
        imgShare.setOnClickListener(this)
        txtSave.setOnClickListener(this)
        txtDelete.setOnClickListener(this)
        txtPlay.setOnClickListener(this)

        requestPermission(permissions, this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabRecord -> {
                handledVoiceRecording()
            }
            R.id.imgBack -> finish()
            R.id.imgShare -> {
                customProgressCircle.stopAnimated()
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()

            }
            R.id.txtDelete -> {
                counterPlay = 1000000000
                status = Status.RECORD
                fabRecord.show()
                txtPlay.visibility = View.GONE
                File(filePath).delete()
                oncePlay = true
                this.player = MediaPlayer()
                this.fabRecord.setImageResource(R.drawable.ic_keyboard_voice)
                this.customProgressCircle.stopAnimated()
                this.txtTime.text = getString(R.string.zero_size)
                this.txtSize.text = getString(R.string.zero_size)
            }
            R.id.txtSave -> {
                showMsg(getString(R.string.save))
                finish()
            }
            R.id.txtPlay -> {
                if (this.player.isPlaying) {
                    player.stop()
                    this.customProgressCircle.stopAnimated()
                    this.fabRecord.setImageResource(R.drawable.ic_play)
                    return
                }
                if (!File(filePath).exists()) {
                    showMsg("problem play voice")
                    return
                }
                if (oncePlay) {
                    this.player.setDataSource(this.filePath)
                    this.player.prepare()
                }
                this.player.start()
                oncePlay = false
                counterThreadPlay()

                this.fabRecord.setImageResource(R.drawable.ic_stop)
                this.customProgressCircle.startAnimated()
            }


        }
    }

    var counter = 0
    var counterPlay = 0
    private fun counterThread() {
        val thread = object : Thread() {
            override fun run() {
                while (status == Status.STOP) {
                    try {
                        Thread.sleep(100)
                        counter += 100
                        runOnUiThread {
                            txtTime.text = convertToTime(counter.toFloat()) + ""
                            txtSize.text = formatFileSize(File(filePath).length())

                        }

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        thread.start()
    }

    private fun threadSize() {
        val file = File(filePath)
        val thread = object : Thread() {
            override fun run() {
                super.run()
                while (status == Status.STOP) {
                    try {
                        Thread.sleep(1500)
                        Log.i("TAGAA", " size : ${file.length() /1024}")

                        runOnUiThread {
                            txtSize.text = formatFileSize(file.length())

                        }

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        thread.start()
    }

    private fun counterThreadPlay() {
        counterPlay = 0

        val thread = object : Thread() {
            override fun run() {
                while (counterPlay <= counter) {
                    try {
                        Thread.sleep(100)
                        counterPlay += 100

                        runOnUiThread { txtTime.text = convertToTime(counterPlay.toFloat()) + "" }

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
                if (counterPlay >= counter) {
                    runOnUiThread {
                        txtTime.text = convertToTime(counter.toFloat()) + ""
                    }
                }
            }
        }
        thread.start()

    }


    private fun handledVoiceRecording() {
        when (status) {
            Status.RECORD -> {
                prepare()
                this.fabRecord.setImageResource(R.drawable.ic_stop)
                this.customProgressCircle.startAnimated()
                this.recorder.start()
                status = Status.STOP
                counterThread()
                threadSize()
            }
            Status.STOP -> {
                this.fabRecord.setImageResource(R.drawable.ic_play)
                this.customProgressCircle.stopAnimated()
                counterPlay = 1000000000
                this.recorder.stop()
                this.recorder.release()
                status = Status.PLAY
                fabRecord.hide()
                txtPlay.visibility = View.VISIBLE
            }
            else -> {
            }
        }

    }

    private fun prepare() {
        this.filePath = String.format("%s%s%s%s", resourceApp!!.getDirSoundSave(), "reminderPro-", randomName(), ".3gp")
        this.recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
        recorder.setAudioEncodingBitRate(320)
        recorder.setOutputFile(filePath)
        try {
            recorder.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
            showMsg(getString(R.string.error_unknown))
            finish()
        }

    }

    override fun onAllow(permission: String) {
        if (checkPermissions(permissions)) {
            prepare()
        }
    }

    override fun onDenied(permission: String) {
        val alertBuilder = AlertDialog.Builder(supportFragmentManager, getString(R.string.permission), getString(R.string.permission_audio))
        alertBuilder.setBtnPositive(getString(R.string.yes), View.OnClickListener {
            requestPermission(permissions, this)
        })
        alertBuilder.setBtnNegative(getString(R.string.no), View.OnClickListener {
            finish()
        })
        alertBuilder.build().show()
    }

    enum class Status {
        RECORD,
        PLAY,
        STOP
    }


}