package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.LocationEntity
import kotlinx.android.synthetic.main.activity_alarm.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/27/19.
 */
class AlarmActivity : BaseActivity() {

    companion object {
      private lateinit  var locationEntity: LocationEntity
        fun start(context: Context, item: LocationEntity) {
            this.locationEntity = item
            val intent = Intent(context, AlarmActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        txtDescription.text = locationEntity.text
        txtTitle.text = locationEntity.title

        btnOk.setOnClickListener {
            finish()
        }
    }
}