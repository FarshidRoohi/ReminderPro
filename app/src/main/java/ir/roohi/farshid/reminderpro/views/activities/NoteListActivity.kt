package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.customViews.CustomRecyclerView
import ir.roohi.farshid.reminderpro.extensions.share
import ir.roohi.farshid.reminderpro.listener.multiSelect.OnMultiSelectNotesListener
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import ir.roohi.farshid.reminderpro.views.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.layout_item_selected.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteListActivity : BaseActivity(), Observer<List<NoteEntity>>, View.OnClickListener,
    OnMultiSelectNotesListener {

    private lateinit var adapter: NoteAdapter
    private lateinit var viewModel: NoteViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NoteListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter = NoteAdapter()
        recycler.adapter = adapter
        recycler.addFab(fabAdd)

        adapter.listener = this

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        viewModel.notes.observe(this, this)

        fabAdd.setOnClickListener(this)
        toolbar.setIconLeftListener(View.OnClickListener { finish() })

        recycler.addOnScrollStateListener(object : CustomRecyclerView.OnScrollStateListener {
            override fun onScrollEnded(recyclerView: CustomRecyclerView) {
            }
        })

    }

    override fun onChanged(list: List<NoteEntity>?) {
        this.progressBar.visibility = View.GONE
        adapter.removeAll()
        list?.apply {
            adapter.swapData(ArrayList(this))
        }

        if (adapter.itemCount == 0) {
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            layoutEmptyState.visibility = View.GONE
        }
    }

    override fun onMultiSelectNotes(items: ArrayList<NoteEntity>) {
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
        } else {
            imgShare.visibility = View.VISIBLE
        }

        imgCancelSelect.setOnClickListener {
            resetData()
        }
        imgShare.setOnClickListener {
            if (items.isEmpty()) {
                return@setOnClickListener
            }
            items[0].text.share(this)
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabAdd -> {
                NoteEditActivity.start(this, null)
            }
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

    override fun onBackPressed() {
        if (layoutSelectItem.visibility == View.VISIBLE) {
            resetData()
            return
        }
        super.onBackPressed()
    }

}