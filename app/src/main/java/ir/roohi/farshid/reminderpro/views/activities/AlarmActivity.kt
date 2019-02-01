package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.service.UserLocationService
import ir.roohi.farshid.reminderpro.viewModel.LocationViewModel
import kotlinx.android.synthetic.main.activity_alarm.*
import java.io.Serializable
import android.view.WindowManager


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/27/19.
 */
class AlarmActivity : BaseActivity() {

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

        val viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationEntity.status = false
        viewModel.update(locationEntity)

        viewModel.liveDateLocations!!.observe(this, Observer<List<LocationEntity>> { list ->
            val items:ArrayList<LocationEntity> = ArrayList(list!!)
            val intent = Intent(this, UserLocationService::class.java)
            intent.putExtra("locationEntity", items as Serializable)
            startService(intent)
        })

        btnOk.setOnClickListener {
            finish()
        }
    }

}