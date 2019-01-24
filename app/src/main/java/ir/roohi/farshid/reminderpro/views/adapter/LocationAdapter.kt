package ir.roohi.farshid.reminderpro.views.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.databinding.ItemLocationBinding
import ir.roohi.farshid.reminderpro.extensions.animatedColorBackgroundSelected
import ir.roohi.farshid.reminderpro.extensions.toAgoTime
import ir.roohi.farshid.reminderpro.listener.OnClickItemLocationListener
import ir.roohi.farshid.reminderpro.listener.multiSelect.OnMultiSelectLocationListener
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.utility.EPrettyTime

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
class LocationAdapter : BaseRecyclerAdapter<LocationEntity>() {

    var onClickListener: OnClickItemLocationListener? = null

    var itemsSelected: ArrayList<LocationEntity> = ArrayList()
    var listenerMultiSelect: OnMultiSelectLocationListener? = null


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
        binding.txtDate.text = element.date.toAgoTime(context!!)
        binding.txtDesc.visibility = View.GONE
        if (!element.text!!.isEmpty()) {
            binding.txtDesc.visibility = View.VISIBLE
        }

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
                val item = getItems()!![viewHolder.adapterPosition]

                if (itemsSelected.contains(getItems()!![viewHolder.adapterPosition])) {
                    item.statusSelect = false
                    itemsSelected.remove(getItems()!![viewHolder.adapterPosition])
                    binding.rootLayout.animatedColorBackgroundSelected(false)
                    listenerMultiSelect?.onMultiSelectLocation(itemsSelected)
                    changeColorDarker(binding)
                    return@setOnClickListener
                }

                getItems()!![viewHolder.adapterPosition].statusSelect = true
                itemsSelected.add(item)
                binding.rootLayout.animatedColorBackgroundSelected()
                listenerMultiSelect?.onMultiSelectLocation(itemsSelected)
                changeColorLight(binding)
                return@setOnClickListener
            }
            binding.rootLayout.setOnLongClickListener {

                val item = getItems()!![viewHolder.adapterPosition]
                if (itemsSelected.contains(getItems()!![viewHolder.adapterPosition])) {
                    item.statusSelect = false
                    itemsSelected.remove(getItems()!![viewHolder.adapterPosition])
                    binding.rootLayout.animatedColorBackgroundSelected(false)
                    changeColorDarker(binding)
                    listenerMultiSelect?.onMultiSelectLocation(itemsSelected)
                }
                item.statusSelect = true
                itemsSelected.add(item)
                binding.rootLayout.animatedColorBackgroundSelected()
                changeColorLight(binding)
                listenerMultiSelect?.onMultiSelectLocation(itemsSelected)
                true
            }


            binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->
                getItems()!![viewHolder.adapterPosition].status = isChecked
                onClickListener!!.onClickItemLocation(viewHolder.adapterPosition, getItems()!![viewHolder.adapterPosition])
            }
        }

    }

    private fun changeColorLight(binding: ItemLocationBinding) {
        binding.txtDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle_light))
        binding.txtDesc.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title_light))
        binding.txtTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title_light))
    }

    private fun changeColorDarker(binding: ItemLocationBinding) {
        binding.txtDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle))
        binding.txtDesc.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_subtitle))
        binding.txtTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_title))
    }

}