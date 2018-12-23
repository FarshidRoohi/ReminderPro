package ir.roohi.farshid.reminderpro.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import ir.roohi.farshid.reminderpro.viewModel.VoiceViewModel
import ir.roohi.farshid.reminderpro.views.adapter.VoiceAdapter
import kotlinx.android.synthetic.main.activity_sound_list.*
import java.util.ArrayList

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */

class SoundListActivity : BaseActivity(), View.OnClickListener, Observer<List<VoiceEntity>> {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SoundListActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_list)

        val viewModel = ViewModelProviders.of(this).get(VoiceViewModel::class.java)

        viewModel.mutableList!!.observe(this, this)

        fabAdd.setOnClickListener(this)
        toolbar.setIconLeftListener(View.OnClickListener {
            finish()
        })
    }

    override fun onChanged(list: List<VoiceEntity>?) {
        list?.let {
            recycler.layoutManager = LinearLayoutManager(this)
            val adapter = VoiceAdapter()
            recycler.adapter = adapter
            adapter.swapData(ArrayList(list))
            return
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabAdd -> RecordSoundActivity.start(this)
        }
    }

}