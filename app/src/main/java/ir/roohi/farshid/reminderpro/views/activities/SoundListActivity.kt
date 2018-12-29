package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import ir.roohi.farshid.reminderpro.viewModel.VoiceViewModel
import ir.roohi.farshid.reminderpro.views.adapter.VoiceAdapter
import kotlinx.android.synthetic.main.activity_sound_list.*
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */

class SoundListActivity : BaseActivity(), Observer<List<VoiceEntity>>, VoiceAdapter.OnClickItemListener {


    private val adapter = VoiceAdapter()
    private val player = MediaPlayer()
    private var positionPlayItem = -1

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

        adapter.listener = this
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        fabAdd.setOnClickListener {
            stopPlay()
            RecordSoundActivity.start(this)
        }

        toolbar.setIconLeftListener(View.OnClickListener {
            stopPlay()
            finish()
        })
    }

    override fun onChanged(list: List<VoiceEntity>?) {
        this.progressBar.visibility = View.GONE
        this.layoutEmptyState.visibility = View.INVISIBLE

        if (list == null || list.isEmpty()) {
            layoutEmptyState.visibility = View.VISIBLE
            return
        }
        list.let {
            adapter.swapData(ArrayList(list))
            return
        }
    }

    override fun onClickItem(element: VoiceEntity, position: Int) {
        if (player.isPlaying) {
            player.reset()
        }
        if (element.isPlaying) {
            element.isPlaying = false
            player.reset()
            adapter.notifyDataSetChanged()
            return
        }
        this.positionPlayItem = position

        adapter.getItems()!!.forEach { it ->
            if (it != element) {
                it.isPlaying = false
            }
        }

        player.setDataSource(element.path)
        player.prepare()
        player.start()
        element.isPlaying = true
        adapter.notifyDataSetChanged()

        player.setOnCompletionListener {
            player.reset()
            adapter.notifyItemChanged(position)
            element.isPlaying = false
        }
    }

    private fun stopPlay() {
        if (positionPlayItem == -1) {
            return
        }
        player.reset()
        adapter.getItems()!![positionPlayItem].isPlaying = false
        adapter.notifyItemChanged(positionPlayItem)
    }

    override fun onBackPressed() {
        stopPlay()
        super.onBackPressed()
    }
}