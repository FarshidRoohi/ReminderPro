package ir.roohi.farshid.reminderpro.views.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemVoBinding
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/24/18.
 */
class VoiceAdapter : BaseRecyclerAdapter<VoiceEntity>() {

    var listener: OnClickItemListener? = null

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_vo
    }

    override fun onBindViewHolderA(viewDataBinding: ViewDataBinding, position: Int, viewType: Int, element: VoiceEntity) {
        val binding = viewDataBinding as ItemVoBinding
        binding.txtTitle.text = element.title
        binding.txtDate.text = element.date.toAgoTime()
        binding.icPlay.setImageResource(if (element.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
        binding.progressBar.max = element.duration / 60

        if (element.isPlaying) {
            binding.layoutPlaying.visibility = View.VISIBLE
            binding.lottieLayer.playAnimation()

            //TODO : change handled voice progressBar show
            val t = object : Thread() {
                override fun run() {
                    super.run()
                    for (i in 0..element.duration) {
                        Thread.sleep(60)
                        binding.progressBar.progress = i
                        if (!element.isPlaying) break
                    }
                }
            }
            t.start()

        } else {
            binding.layoutPlaying.visibility = View.GONE
            binding.lottieLayer.cancelAnimation()
        }

        binding.rootLayout.setOnClickListener {
            listener?.onClickItem(element, position)!!
        }
    }

    interface OnClickItemListener {
        fun onClickItem(item: VoiceEntity, position: Int)
    }

}