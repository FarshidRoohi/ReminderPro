package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.listener.OnClickItemLocationListener
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.viewModel.LocationViewModel
import ir.roohi.farshid.reminderpro.views.adapter.LocationAdapter
import kotlinx.android.synthetic.main.activity_reminder_location.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class LocationListActivity : BaseActivity() {

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

        val viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        val adapter = LocationAdapter()
        adapter.listener = object : OnClickItemLocationListener {
            override fun onClickItemLocation(position: Int, element: LocationEntity) {
                adapter.getItems()!![position].status = element.status
//                adapter.notifyItemChanged(position)
                viewModel.update(element)
            }
        }
        recycler.adapter = adapter

        viewModel.liveDateLocations!!.observe(this, Observer<List<LocationEntity>> { list ->

            layoutEmptyState.visibility = View.GONE
            progressBar.visibility = View.GONE

            if (list == null || list.isEmpty()) {
                layoutEmptyState.visibility = View.VISIBLE
                return@Observer
            }
            adapter.swapData(ArrayList(list))

        })

    }

}
