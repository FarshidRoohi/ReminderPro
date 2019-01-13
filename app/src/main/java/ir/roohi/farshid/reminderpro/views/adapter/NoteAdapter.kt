package ir.roohi.farshid.reminderpro.views.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemNoteBinding
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.views.activities.NoteEditActivity


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteAdapter : BaseRecyclerAdapter<NoteEntity>() {

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_note
    }

    override fun onBindView(viewDataBinding: ViewDataBinding,viewHolder: RecyclerView.ViewHolder, position: Int, viewType: Int, element: NoteEntity) {
        val binding = viewDataBinding as ItemNoteBinding
        binding.txtDate.text = element.date.toAgoTime()
        binding.txtTitle.text = element.title
        binding.rootLayout.setOnClickListener {
            NoteEditActivity.start(context!!, element)
        }
    }

}