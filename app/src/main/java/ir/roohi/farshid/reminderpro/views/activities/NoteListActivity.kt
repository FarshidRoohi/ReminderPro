package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.CustomRecyclerView
import ir.roohi.farshid.reminderpro.extensions.share
import ir.roohi.farshid.reminderpro.listener.OnMultiSelectNotesListener
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import ir.roohi.farshid.reminderpro.views.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_note_list.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteListActivity : BaseActivity(), Observer<List<NoteEntity>>, View.OnClickListener, OnMultiSelectNotesListener {

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
            items[0].text.share(this)
        }
        imgDelete.setOnClickListener {
            items.forEach { item ->
                viewModel.remove(item)
            }
            layoutSelectItem.visibility = View.GONE
            items.clear()
        }


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabAdd -> {
                NoteEditActivity.start(this, null)
            }
            R.id.imgBack -> {
                finish()
            }
        }
    }
}