package ir.roohi.farshid.reminderpro.views.adapter

import androidx.databinding.ViewDataBinding
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemLocationBinding
import ir.roohi.farshid.reminderpro.model.LocationEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
class LocationAdapter : BaseRecyclerAdapter<LocationEntity>() {
    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_location
    }

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, position: Int, viewType: Int, element: LocationEntity) {
        val binding = viewDataBinding as ItemLocationBinding

        binding.txtTitle.text = element.title

    }


}