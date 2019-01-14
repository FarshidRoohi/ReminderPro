package ir.roohi.farshid.reminderpro.views.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemNoteBinding
import ir.roohi.farshid.reminderpro.extensions.animatedColorBackgroundSelected
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.listener.OnMultiSelectNotesListener
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.views.activities.NoteEditActivity


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteAdapter : BaseRecyclerAdapter<NoteEntity>() {

    private var itemsSelected: ArrayList<NoteEntity> = ArrayList()
    var listener: OnMultiSelectNotesListener? = null

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_note
    }

    override fun onBindView(
        viewDataBinding: ViewDataBinding,
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
        viewType: Int,
        element: NoteEntity
    ) {
        val binding = viewDataBinding as ItemNoteBinding
        binding.txtDate.text = element.date.toAgoTime()
        binding.txtTitle.text = element.title

        val colorSelectItem =
            ContextCompat.getColor(binding.rootLayout.context, R.color.color_background_select_item_recycler_view)
        val color = if (element.statusSelect) colorSelectItem else Color.TRANSPARENT

        binding.rootLayout.setBackgroundColor(color)

        binding.rootLayout.setOnClickListener {
            if (itemsSelected.size > 0) {
                val item = getItems()!![viewHolder.adapterPosition]

                if (itemsSelected.contains(getItems()!![viewHolder.adapterPosition])) {
                    item.statusSelect = false
                    itemsSelected.remove(getItems()!![viewHolder.adapterPosition])
                    binding.rootLayout.animatedColorBackgroundSelected(false)
                    listener?.onMultiSelectNotes(itemsSelected)
                    changeColorDarker(binding)
                    return@setOnClickListener
                }

                getItems()!![viewHolder.adapterPosition].statusSelect = true
                itemsSelected.add(item)
                binding.rootLayout.animatedColorBackgroundSelected()
                listener?.onMultiSelectNotes(itemsSelected)
                changeColorLight(binding)
                return@setOnClickListener
            }
            NoteEditActivity.start(context!!, element)
        }

        binding.rootLayout.setOnLongClickListener {

            val item = getItems()!![viewHolder.adapterPosition]
            if (itemsSelected.contains(getItems()!![viewHolder.adapterPosition])) {
                item.statusSelect = false
                itemsSelected.remove(getItems()!![viewHolder.adapterPosition])
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