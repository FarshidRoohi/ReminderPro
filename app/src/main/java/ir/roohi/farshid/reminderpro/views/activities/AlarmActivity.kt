package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.service.UserLocationService
import ir.roohi.farshid.reminderpro.viewModel.LocationViewModel
import kotlinx.android.synthetic.main.activity_alarm.*
import java.io.Serializable


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/27/19.
 */
class AlarmActivity : BaseActivity() {

    private lateinit var viewModel: LocationViewModel
    private lateinit var vibrator: Vibrator
    private lateinit var mediaPlayer: MediaPlayer

    companion object {
        private lateinit var locationEntity: LocationEntity
        fun start(context: Context, item: LocationEntity) {
            this.locationEntity = item
            val intent = Intent(context, AlarmActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        setContentView(R.layout.activity_alarm)

        txtDescription.text = locationEntity.text
        txtTitle.text = locationEntity.title


        this.mediaPlayer = MediaPlayer.create(this, R.raw.ton)
        this.mediaPlayer.isLooping = true
        this.mediaPlayer.start()

        this.vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val pattern = longArrayOf(0, 100, 1000, 300, 200, 100, 500, 200, 100)
        this.vibrator.vibrate(pattern, 0)

        btnOk.setOnClickListener {
            vibrator.cancel()
            mediaPlayer.stop()
            finish()
        }

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        update()
        var flagObserverUpdate = false
        viewModel.liveDateLocations!!.observe(this, Observer<List<LocationEntity>> { list ->
            if (flagObserverUpdate) {
                val items: ArrayList<LocationEntity> = ArrayList(list!!)
                items.forEach {
                    if (it.id == locationEntity.id && !it.status) {
                        val intent = Intent(this, UserLocationService::class.java)
                        intent.putExtra("locationEntity", items as Serializable)
                        startService(intent)
                        return@forEach
                    }
                }
            }
            flagObserverUpdate = true
        })


    }

    private fun update() {
        locationEntity.status = false
        viewModel.update(locationEntity)
    }

    override fun onDestroy() {
        vibrator.cancel()
        mediaPlayer.stop()
        super.onDestroy()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) {
            return
        }
//        update()
    }

}