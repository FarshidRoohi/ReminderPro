package ir.roohi.farshid.reminderpro.views.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemLocationBinding
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.listener.OnClickItemLocationListener
import ir.roohi.farshid.reminderpro.model.LocationEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
class LocationAdapter : BaseRecyclerAdapter<LocationEntity>() {

    var listener: OnClickItemLocationListener? = null


    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_location
    }

    override fun onBindView(
        viewDataBinding: ViewDataBinding,
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
        viewType: Int,
        element: LocationEntity
    ) {

        val binding = viewDataBinding as ItemLocationBinding
        binding.switchCompat.isChecked = element.status
        binding.txtTitle.text = element.title
        binding.txtDesc.text = element.text
        binding.txtDate.text = element.date.toAgoTime()
        binding.txtDesc.visibility = View.GONE

        if (!element.text!!.isEmpty()) {
            binding.txtDesc.visibility = View.VISIBLE
        }

        binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->
            getItems()!![viewHolder.adapterPosition].status = isChecked
            listener!!.onClickItemLocation(viewHolder.adapterPosition, getItems()!![viewHolder.adapterPosition])
        }
    }


}