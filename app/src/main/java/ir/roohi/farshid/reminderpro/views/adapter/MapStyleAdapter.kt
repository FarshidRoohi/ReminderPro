package ir.roohi.farshid.reminderpro.views.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import ir.farshid_roohi.customadapterrecycleview.AdapterRecyclerView
import ir.farshid_roohi.customadapterrecycleview.viewHolder.ItemViewHolder
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.Storage
import ir.roohi.farshid.reminderpro.databinding.ItemMapStyleBinding
import ir.roohi.farshid.reminderpro.keys.MAP_STYLE_URL
import ir.roohi.farshid.reminderpro.model.MapStyle

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/18/19.
 */
class MapStyleAdapter : AdapterRecyclerView<MapStyle>() {

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_map_style
    }

    @SuppressLint("WrongConstant")
    override fun onBindView(
        viewDataBinding: ViewDataBinding, viewHolder:ItemViewHolder, position: Int,
        viewType: Int,
        element: MapStyle
    ) {

        val binding = viewDataBinding as ItemMapStyleBinding

        binding.txtTitle.text = element.title
        binding.imgMap.setImageResource(element.icon)
        binding.layoutRoot.setBackgroundColor(Color.TRANSPARENT)
        binding.txtTitle.setTextColor(getContextCompatColor(R.color.color_subtitle))

        if (binding.root.context.resources.configuration.layoutDirection == 0) {
            binding.layoutRoot.layoutDirection = ViewCompat.LAYOUT_DIRECTION_LTR
        } else {
            binding.layoutRoot.layoutDirection = ViewCompat.LAYOUT_DIRECTION_RTL
        }

        if (element.status!!) {
            binding.layoutRoot.setBackgroundColor(getContextCompatColor(R.color.colorAccent))
            binding.txtTitle.setTextColor(Color.WHITE)
        }

        binding.layoutRoot.setOnClickListener {
            items!!.forEach { item ->
                item.status = false
            }
            items!![viewHolder.adapterPosition].status = true
            binding.layoutRoot.setBackgroundColor(getContextCompatColor(R.color.colorAccent))
            binding.txtTitle.setTextColor(Color.WHITE)

            Handler().post {
                notifyDataSetChanged()
            }

            Storage(viewDataBinding.root.context).put(
                String::class.java,
                MAP_STYLE_URL,
                items!![viewHolder.adapterPosition].url!!
            )
        }


    }
    private  fun getContextCompatColor(color: Int): Int {
        return ContextCompat.getColor(context!!, color)
    }


}