package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.listener.OnClickItemLocationListener
import ir.roohi.farshid.reminderpro.listener.OnInformationLocationListener
import ir.roohi.farshid.reminderpro.listener.multiSelect.OnMultiSelectLocationListener
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.service.UserLocationService
import ir.roohi.farshid.reminderpro.viewModel.LocationViewModel
import ir.roohi.farshid.reminderpro.views.adapter.LocationAdapter
import ir.roohi.farshid.reminderpro.views.bottomSheet.InformationLocationBottomSheet
import kotlinx.android.synthetic.main.activity_reminder_location.*
import kotlinx.android.synthetic.main.layout_item_selected.*
import java.util.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class LocationListActivity : BaseActivity(),
    OnMultiSelectLocationListener {

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

        toolbar.getLeftImageView().setOnClickListener {
            finish()
        }
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
                val intent = Intent(this@LocationListActivity, UserLocationService::class.java)
                intent.putExtra("locationEntity", element)
                startService(intent)
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
        txtCounterSelect.text = String.format(getString(R.string.selected_number), items.size.toString())
        setStatusBarColor(R.color.black)
        if (layoutSelectItem.visibility == View.GONE) {
            layoutSelectItem.visibility = View.VISIBLE
        }

        if (items.size > 1) {
            imgShare.visibility = View.GONE
            imgEdit.visibility = View.GONE
        } else {
            imgShare.visibility = View.VISIBLE
            imgEdit.visibility = View.VISIBLE
        }

        imgEdit.setOnClickListener {
            edit(items.first())
        }

        imgCancelSelect.setOnClickListener {
            resetData()
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
            val alertDialog = AlertDialog.Builder(
                supportFragmentManager,
                getString(R.string.note), getString(R.string.do_you_sure_delete)
            )
            alertDialog.setBtnNegative(getString(R.string.no), View.OnClickListener {
                alertDialog.dialog!!.dismiss()
            })
            alertDialog.setBtnPositive(getString(R.string.yes), View.OnClickListener {
                items.forEach { item ->
                    viewModel.remove(item)
                }
                layoutSelectItem.visibility = View.GONE
                setStatusBarColor(R.color.colorPrimaryDark)
                items.clear()
                alertDialog.dialog!!.dismissAllowingStateLoss()
            })
            alertDialog.build().show()

        }
    }

    private fun resetData() {
        adapter.itemsSelected.clear()
        adapter.getItems()?.forEach { item ->
            item.statusSelect = false
        }
        adapter.notifyDataSetChanged()
        layoutSelectItem.visibility = View.GONE
        setStatusBarColor(R.color.colorPrimaryDark)
    }

    private fun edit(item: LocationEntity) {
        val bottomSheet =
            InformationLocationBottomSheet(supportFragmentManager, object : OnInformationLocationListener {
                override fun onInformationLocation(title: String, desc: String?, distance: Int) {
                    item.distance = distance
                    item.title = title
                    item.text = desc
                    viewModel.update(item)
                    resetData()
                }
            })
        bottomSheet.modelMap = item
        bottomSheet.show()
    }

    override fun onBackPressed() {
        if (layoutSelectItem.visibility == View.VISIBLE) {
            resetData()
            return
        }
        super.onBackPressed()
    }

}
