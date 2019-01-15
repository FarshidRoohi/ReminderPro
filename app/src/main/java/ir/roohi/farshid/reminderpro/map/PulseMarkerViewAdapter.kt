package ir.roohi.farshid.reminderpro.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mapbox.mapboxsdk.maps.MapboxMap
import ir.roohi.farshid.reminderpro.R


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/11/19.
 */

class PulseMarkerViewAdapter(myContext: Context) :
    MapboxMap.MarkerViewAdapter<PulseMarkerView>(myContext, PulseMarkerView::class.java) {

    override fun getView(marker: PulseMarkerView, convertView: View?, parent: ViewGroup): View? {
        val viewHolder: ViewHolder
        var view: View? = convertView
        if (view == null) {
            viewHolder = ViewHolder()
            view = LayoutInflater.from(context).inflate(R.layout.view_pulse_marker, parent, false)
            viewHolder.foregroundImageView = view!!.findViewById(R.id.foreground_imageView)
            viewHolder.backgroundImageView = view.findViewById(R.id.background_imageview)
            view.tag = viewHolder
        }


        return view
    }

    class ViewHolder {
        var foregroundImageView: ImageView? = null
        var backgroundImageView: ImageView? = null

    }
}