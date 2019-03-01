package ir.roohi.farshid.reminderpro.views.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup.OnCheckedChangeListener
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.Storage
import ir.roohi.farshid.reminderpro.keys.MAP_STYLE_URL
import ir.roohi.farshid.reminderpro.model.MapStyle
import ir.roohi.farshid.reminderpro.views.adapter.MapStyleAdapter
import kotlinx.android.synthetic.main.activity_settings.*
import org.greenrobot.eventbus.EventBus


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/17/19.
 */
class SettingsActivity : BaseActivity(), OnCheckedChangeListener {

    private var currentIdSelect = R.id.choiceEn
    private var numberPlusCrash = 0

    override fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int) {
        if (currentIdSelect != checkedId) {
            currentIdSelect = checkedId

            currentLanguage = if (currentIdSelect == R.id.choiceFa) "FA" else "EN"
            sharedPreferences!!.edit().putString("LANGUAGE", currentLanguage!!).apply()
            setLocale(this.currentLanguage!!)
            EventBus.getDefault().post("change language $currentLanguage")
        }
    }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbar.rotateLayoutDirection()
        toolbar.getLeftImageView().setOnClickListener { finish() }

        toolbar.getTxtTitle().setOnClickListener {
            numberPlusCrash++
            if (numberPlusCrash == 10) {
                var temp = arrayOf("a", "b")
                var c = temp[3]
            }
            if (numberPlusCrash > 6) {
                showMsg("crash : $numberPlusCrash")
            }
        }

        groupToggle.setOnCheckedChangeListener(this)
        currentIdSelect = if (this.currentLanguage == "FA") R.id.choiceFa else R.id.choiceEn
        groupToggle.check(currentIdSelect)

        val adapter = MapStyleAdapter()
        adapter.swapData(populate())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setItemViewCacheSize(0)

        txtFeedback.setOnClickListener {
            startActivity(Intent(this, FeedbackActivity::class.java))
        }


    }

    private fun populate(): ArrayList<MapStyle> {

        val items = ArrayList<MapStyle>()
        val url = Storage(this)[String::class.java, MAP_STYLE_URL]

        items.add(
            MapStyle(
                getString(R.string.map_style_basic),
                url == null,
                "mapbox://styles/farshidroohi/cjr2f00e66xyz2srs69kwkmuz",
                R.drawable.map_basic
            )
        )
        items.add(
            MapStyle(
                getString(R.string.map_style_ice_cream),
                false,
                "mapbox://styles/farshidroohi/cjr2fa8y70mtt2smscq7rtyu1",
                R.drawable.map_ice_cream
            )
        )
        items.add(
            MapStyle(
                getString(R.string.map_style_satellite),
                false,
                "mapbox://styles/farshidroohi/cjr2hxqpd0p7v2spbeeql8cu2",
                R.drawable.map_satellite_streets
            )
        )
        items.add(
            MapStyle(
                getString(R.string.map_style_dark),
                false,
                "mapbox://styles/farshidroohi/cjqpsdw2v19br2sqwpr1m10ta",
                R.drawable.map_dark
            )
        )
        items.add(
            MapStyle(
                getString(R.string.map_style_light),
                false,
                "mapbox://styles/farshidroohi/cjr2hyg7m024v2soe6mj07v5p",
                R.drawable.map_light
            )
        )
        items.add(
            MapStyle(
                getString(R.string.map_style_decimal),
                false,
                "mapbox://styles/farshidroohi/cjqpsbsar015p2qmk8zmwqkxy",
                R.drawable.map_decimal
            )
        )

        if (url != null) {
            items.forEach {
                if (it.url == url) {
                    it.status = true
                    return@forEach
                }
            }
        }

        return items
    }
}