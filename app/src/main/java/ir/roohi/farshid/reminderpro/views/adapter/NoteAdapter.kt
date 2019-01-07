package ir.roohi.farshid.reminderpro.views.adapter

import androidx.databinding.ViewDataBinding
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemNotBinding
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.views.activities.NoteEditActivity


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteAdapter : BaseRecyclerAdapter<NoteEntity>() {

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_not
    }

    override fun onBindViewHolderA(viewDataBinding: ViewDataBinding, position: Int, viewType: Int, element: NoteEntity) {
        val binding = viewDataBinding as ItemNotBinding
        binding.txtDate.text = element.date.toAgoTime()
        binding.txtTitle.text = element.title
        binding.rootLayout.setOnClickListener {
            NoteEditActivity.start(context!!, element)
        }
    }

}