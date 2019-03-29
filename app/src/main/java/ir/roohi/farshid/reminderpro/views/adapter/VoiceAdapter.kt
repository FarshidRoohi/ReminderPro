package ir.roohi.farshid.reminderpro.views.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import ir.farshid_roohi.customadapterrecycleview.AdapterRecyclerView
import ir.farshid_roohi.customadapterrecycleview.viewHolder.ItemViewHolder
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemVoiceBinding
import ir.roohi.farshid.reminderpro.extensions.animatedColorBackgroundSelected
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.listener.multiSelect.OnMultiSelectVoiceListener
import ir.roohi.farshid.reminderpro.model.VoiceEntity
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/24/18.
 */
class VoiceAdapter : AdapterRecyclerView<VoiceEntity>() {

    var onItemClickListener: OnClickItemListener? = null
    var itemsSelected: ArrayList<VoiceEntity> = ArrayList()
    var listenerMultiSelect: OnMultiSelectVoiceListener? = null

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_voice
    }

    override fun onBindView(
        viewDataBinding: ViewDataBinding,
        viewHolder: ItemViewHolder,
        position: Int,
        viewType: Int,
        element: VoiceEntity
    ) {
        val binding = viewDataBinding as ItemVoiceBinding
        binding.txtTitle.text = element.title
        binding.txtDate.text = element.date.toAgoTime(context!!)
        binding.icPlay.setImageResource(if (element.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
        binding.seekBar.max = element.duration / 60


        val colorSelectItem =
            ContextCompat.getColor(binding.rootLayout.context, R.color.color_background_select_item_recycler_view)
        val color = if (element.statusSelect) colorSelectItem else Color.TRANSPARENT

        binding.rootLayout.setBackgroundColor(color)

        if (element.statusSelect) {
            changeColorLight(binding)
        } else {
            changeColorDarker(binding)
        }

        if (element.isPlaying) {
            binding.layoutPlaying.visibility = View.VISIBLE
            binding.lottieLayer.playAnimation()

            //TODO : change handled voice progressBar show
            val t = object : Thread() {
                override fun run() {
                    super.run()
                    for (i in 0..element.duration) {
                        Thread.sleep(60)
                        binding.seekBar.progress = i
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
            if (itemsSelected.size > 0) {
                val item = items!![viewHolder.adapterPosition]

                if (itemsSelected.contains(item)) {
                    item.statusSelect = false
                    itemsSelected.remove(item)
                    binding.rootLayout.animatedColorBackgroundSelected(false)
                    listenerMultiSelect?.onMultiSelectVoice(itemsSelected)
                    changeColorDarker(binding)
                    return@setOnClickListener
                }

                item.statusSelect = true
                itemsSelected.add(item)
                binding.rootLayout.animatedColorBackgroundSelected()
                listenerMultiSelect?.onMultiSelectVoice(itemsSelected)
                changeColorLight(binding)
                return@setOnClickListener
            }
            onItemClickListener?.onClickItem(element, position)!!
        }

        binding.rootLayout.setOnLongClickListener {

            val item = items!![viewHolder.adapterPosition]
            if (itemsSelected.contains(item)) {
                item.statusSelect = false
                itemsSelected.remove(item)
                binding.rootLayout.animatedColorBackgroundSelected(false)
                changeColorDarker(binding)
                listenerMultiSelect?.onMultiSelectVoice(itemsSelected)
            }
            item.statusSelect = true
            itemsSelected.add(item)
            binding.rootLayout.animatedColorBackgroundSelected()
            changeColorLight(binding)
            listenerMultiSelect?.onMultiSelectVoice(itemsSelected)
            true
        }

    }

    private fun changeColorLight(binding: ItemVoiceBinding) {
        binding.txtDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle_light))
        binding.txtTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title_light))
    }

    private fun changeColorDarker(binding: ItemVoiceBinding) {
        binding.txtDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle))
        binding.txtTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title))
    }

    interface OnClickItemListener {
        fun onClickItem(item: VoiceEntity, position: Int)
    }

}