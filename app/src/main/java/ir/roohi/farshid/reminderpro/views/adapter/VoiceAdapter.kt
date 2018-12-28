package ir.roohi.farshid.reminderpro.views.adapter

import androidx.databinding.ViewDataBinding
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemVoiceBinding
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/24/18.
 */
class VoiceAdapter : BaseRecyclerAdapter<VoiceEntity>() {

    var listener: OnClickItemListener? = null

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_voice
    }

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, position: Int, viewType: Int, element: VoiceEntity) {
        val binding = viewDataBinding as ItemVoiceBinding
        binding.txtTitle.text = element.title
        binding.txtDate.text = element.date!!.toString()
        binding.icPlay.setImageResource(if (element.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)

        binding.rootLayout.setOnClickListener {
            listener?.onClickItem(element,position)
        }
    }

    interface OnClickItemListener {
        fun onClickItem(item: VoiceEntity,position: Int)
    }

}