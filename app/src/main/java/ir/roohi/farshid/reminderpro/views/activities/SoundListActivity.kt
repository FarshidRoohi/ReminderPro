package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.listener.multiSelect.OnMultiSelectVoiceListener
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import ir.roohi.farshid.reminderpro.viewModel.VoiceViewModel
import ir.roohi.farshid.reminderpro.views.adapter.VoiceAdapter
import kotlinx.android.synthetic.main.activity_sound_list.*
import kotlinx.android.synthetic.main.layout_item_selected.*
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import android.os.StrictMode



/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/23/18.
 */

class SoundListActivity : BaseActivity(), Observer<List<VoiceEntity>>, VoiceAdapter.OnClickItemListener,
    OnMultiSelectVoiceListener {

    private lateinit var viewModel: VoiceViewModel

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

        viewModel = ViewModelProviders.of(this).get(VoiceViewModel::class.java)
        viewModel.mutableList!!.observe(this, this)

        adapter.onItemClickListener = this
        adapter.listenerMultiSelect = this
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
            adapter.removeAll()
            return
        }
        list.let {
            adapter.swapData(ArrayList(list))
            return
        }
    }

    override fun onClickItem(item: VoiceEntity, position: Int) {
        if (player.isPlaying) {
            player.reset()
        }
        if (item.isPlaying) {
            item.isPlaying = false
            player.reset()
            adapter.notifyDataSetChanged()
            return
        }
        this.positionPlayItem = position

        adapter.getItems()!!.forEach { it ->
            if (it != item) {
                it.isPlaying = false
            }
        }

        player.setDataSource(item.path)
        player.prepare()
        player.start()
        item.isPlaying = true
        adapter.notifyDataSetChanged()

        player.setOnCompletionListener {
            player.reset()
            adapter.notifyItemChanged(position)
            item.isPlaying = false
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

    override fun onMultiSelectVoice(items: ArrayList<VoiceEntity>) {
        stopPlay()
        if (items.size == 0) {
            layoutSelectItem.visibility = View.GONE
            return
        }
        txtCounterSelect.text = String.format(getString(R.string.selected_number), items.size.toString())
        setStatusBarColor(R.color.black)
        if (layoutSelectItem.visibility == View.GONE) {
            layoutSelectItem.visibility = View.VISIBLE
        }

        if (items.size > 1) {
            imgShare.visibility = View.GONE
        } else {
            imgShare.visibility = View.VISIBLE
        }

        imgCancelSelect.setOnClickListener {
            resetData()
        }
        imgShare.setOnClickListener {
            if (items.isEmpty()) {
                return@setOnClickListener
            }
            val share = Intent(Intent.ACTION_SEND)
            share.type = "audio/*"
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(items[0].path)))
            startActivity(Intent.createChooser(share, "Share Sound File"))

        }
        imgDelete.setOnClickListener {

            val alertDialog = AlertDialog.Builder(
                supportFragmentManager,
                getString(R.string.note), getString(R.string.do_you_sure_delete)
            )
            alertDialog.setBtnNegative(getString(R.string.no), View.OnClickListener {
                alertDialog.dialog!!.dismiss()
            })
            alertDialog.setBtnPositive(getString(R.string.yes), View.OnClickListener {

                val ex = Executors.newSingleThreadExecutor()
                items.forEach { item ->
                    viewModel.remove(item)
                    ex.execute { File(item.path).deleteOnExit() }
                }
                items.clear()
                layoutSelectItem.visibility = View.GONE
                setStatusBarColor(R.color.colorPrimaryDark)
                alertDialog.dialog!!.dismissAllowingStateLoss()
            })
            alertDialog.build().show()


        }
    }
    private fun resetData() {
        adapter.itemsSelected.clear()
        adapter.getItems()?.forEach { item ->
            item.statusSelect = false
        }
        adapter.notifyDataSetChanged()
        layoutSelectItem.visibility = View.GONE
        setStatusBarColor(R.color.colorPrimaryDark)
    }

    override fun onBackPressed() {
        if (layoutSelectItem.visibility == View.VISIBLE) {
            resetData()
            return
        }
        stopPlay()
        super.onBackPressed()
    }
}