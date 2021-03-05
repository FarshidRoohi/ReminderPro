package ir.roohi.farshid.reminderpro.views.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import ir.farshid_roohi.customadapterrecycleview.AdapterRecyclerView
import ir.farshid_roohi.customadapterrecycleview.viewHolder.ItemViewHolder
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemNoteBinding
import ir.roohi.farshid.reminderpro.utility.animatedColorBackgroundSelected
import ir.roohi.farshid.reminderpro.utility.toPrettyTime
import ir.roohi.farshid.reminderpro.listener.multiSelect.OnMultiSelectNotesListener
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.views.activities.NoteEditActivity


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteAdapter : AdapterRecyclerView<NoteEntity>() {

    var itemsSelected: ArrayList<NoteEntity> = ArrayList()
    var listener: OnMultiSelectNotesListener? = null

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_note
    }

    override fun onBindView(
        viewDataBinding: ViewDataBinding,
        viewHolder: ItemViewHolder,
        position: Int,
        viewType: Int,
        element: NoteEntity
    ) {
        val binding = viewDataBinding as ItemNoteBinding
        binding.txtDate.text = element.date.toPrettyTime(context!!)
        binding.txtTitle.text = if (element.title.isEmpty()) element.text else element.title

        val colorSelectItem =
            ContextCompat.getColor(binding.rootLayout.context, R.color.color_background_select_item_recycler_view)
        val color = if (element.statusSelect) colorSelectItem else Color.TRANSPARENT

        binding.rootLayout.setBackgroundColor(color)

        if (element.statusSelect) {
            changeColorLight(binding)
        } else {
            changeColorDarker(binding)
        }

        binding.rootLayout.setOnClickListener {
            if (itemsSelected.size > 0) {
                val item = items!![viewHolder.adapterPosition]

                if (itemsSelected.contains(items!![viewHolder.adapterPosition])) {
                    item.statusSelect = false
                    itemsSelected.remove(items!![viewHolder.adapterPosition])
                    binding.rootLayout.animatedColorBackgroundSelected(false)
                    listener?.onMultiSelectNotes(itemsSelected)
                    changeColorDarker(binding)
                    return@setOnClickListener
                }

                items!![viewHolder.adapterPosition].statusSelect = true
                itemsSelected.add(item)
                binding.rootLayout.animatedColorBackgroundSelected()
                listener?.onMultiSelectNotes(itemsSelected)
                changeColorLight(binding)
                return@setOnClickListener
            }
            NoteEditActivity.start(context!!, element)
        }

        binding.rootLayout.setOnLongClickListener {

            val item = items!![viewHolder.adapterPosition]
            if (itemsSelected.contains(items!![viewHolder.adapterPosition])) {
                item.statusSelect = false
                itemsSelected.remove(items!![viewHolder.adapterPosition])
                binding.rootLayout.animatedColorBackgroundSelected(false)
                changeColorDarker(binding)
                listener?.onMultiSelectNotes(itemsSelected)
            }
            item.statusSelect = true
            itemsSelected.add(item)
            binding.rootLayout.animatedColorBackgroundSelected()
            changeColorLight(binding)
            listener?.onMultiSelectNotes(itemsSelected)
            true
        }
    }

    private fun changeColorLight(binding: ItemNoteBinding) {
        binding.txtDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle_light))
        binding.txtTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title_light))
    }

    private fun changeColorDarker(binding: ItemNoteBinding) {
        binding.txtDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle))
        binding.txtTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title))
    }

}