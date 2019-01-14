package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.listener.OnClickItemLocationListener
import ir.roohi.farshid.reminderpro.listener.OnMultiSelectLocationListener
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.viewModel.LocationViewModel
import ir.roohi.farshid.reminderpro.views.adapter.LocationAdapter
import kotlinx.android.synthetic.main.activity_reminder_location.*
import java.util.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class LocationListActivity : BaseActivity(), OnMultiSelectLocationListener {

    private lateinit var adapter: LocationAdapter
    private lateinit var viewModel: LocationViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LocationListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_location)

        toolbar.getLeftImageView().setOnClickListener { finish() }
        fabAdd.setOnClickListener {
            SelectPlaceActivity.start(this)
        }

        recycler.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        adapter = LocationAdapter()
        adapter.listenerMultiSelect = this
        adapter.onClickListener = object : OnClickItemLocationListener {
            override fun onClickItemLocation(position: Int, element: LocationEntity) {
                viewModel.update(element)
            }
        }
        recycler.adapter = adapter

        viewModel.liveDateLocations!!.observe(this, Observer<List<LocationEntity>> { list ->

            layoutEmptyState.visibility = View.GONE
            progressBar.visibility = View.GONE
            if (list == null || list.isEmpty()) {
                adapter.removeAll()
                layoutEmptyState.visibility = View.VISIBLE
                return@Observer
            }
            if (list.size == adapter.itemCount) {
                return@Observer
            }
            adapter.swapData(ArrayList(list))

        })
    }


    override fun onMultiSelectLocation(items: ArrayList<LocationEntity>) {
        if (items.size == 0) {
            layoutSelectItem.visibility = View.GONE
            return
        }
        txtCounterSelect.text = items.size.toString()
        if (layoutSelectItem.visibility == View.GONE) {
            layoutSelectItem.visibility = View.VISIBLE
        }

        if (items.size > 1) {
            imgShare.visibility = View.GONE
        } else {
            imgShare.visibility = View.VISIBLE
        }

        imgCancelSelect.setOnClickListener {
            adapter.itemsSelected.clear()
            adapter.getItems()?.forEach { item ->
                item.statusSelect = false
            }
            adapter.notifyDataSetChanged()
            layoutSelectItem.visibility = View.GONE
        }
        imgShare.setOnClickListener {
            if (items.isEmpty()) {
                return@setOnClickListener
            }
            val uri = String.format(Locale.ENGLISH, "geo:%f,%f", items[0].latitude, items[0].longitude)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)

        }
        imgDelete.setOnClickListener {
            items.forEach { item ->
                viewModel.remove(item)
            }
            layoutSelectItem.visibility = View.GONE
            items.clear()
        }


    }

}
