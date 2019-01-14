package ir.roohi.farshid.reminderpro.views.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemVoiceBinding
import ir.roohi.farshid.reminderpro.extensions.animatedColorBackgroundSelected
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.listener.OnMultiSelectVoiceListener
import ir.roohi.farshid.reminderpro.model.VoiceEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/24/18.
 */
class VoiceAdapter : BaseRecyclerAdapter<VoiceEntity>() {

    var onItemClickListener: OnClickItemListener? = null
    var itemsSelected: ArrayList<VoiceEntity> = ArrayList()
    var listenerMultiSelect: OnMultiSelectVoiceListener? = null

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_voice
    }

    override fun onBindView(
        viewDataBinding: ViewDataBinding,
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
        viewType: Int,
        element: VoiceEntity
    ) {
        val binding = viewDataBinding as ItemVoiceBinding
        binding.txtTitle.text = element.title
        binding.txtDate.text = element.date.toAgoTime()
        binding.icPlay.setImageResource(if (element.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
        binding.progressBar.max = element.duration / 60


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
            if (itemsSelected.size > 0) {
                val item = getItems()!![viewHolder.adapterPosition]

                if (itemsSelected.contains(getItems()!![viewHolder.adapterPosition])) {
                    item.statusSelect = false
                    itemsSelected.remove(getItems()!![viewHolder.adapterPosition])
                    binding.rootLayout.animatedColorBackgroundSelected(false)
                    listenerMultiSelect?.onMultiSelectVoice(itemsSelected)
                    changeColorDarker(binding)
                    return@setOnClickListener
                }

                getItems()!![viewHolder.adapterPosition].statusSelect = true
                itemsSelected.add(item)
                binding.rootLayout.animatedColorBackgroundSelected()
                listenerMultiSelect?.onMultiSelectVoice(itemsSelected)
                changeColorLight(binding)
                return@setOnClickListener
            }
            onItemClickListener?.onClickItem(element, position)!!
        }

        binding.rootLayout.setOnLongClickListener {

            val item = getItems()!![viewHolder.adapterPosition]
            if (itemsSelected.contains(getItems()!![viewHolder.adapterPosition])) {
                item.statusSelect = false
                itemsSelected.remove(getItems()!![viewHolder.adapterPosition])
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