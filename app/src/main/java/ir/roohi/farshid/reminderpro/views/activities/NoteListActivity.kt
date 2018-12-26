package ir.roohi.farshid.reminderpro.views.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.CustomRecyclerView
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import ir.roohi.farshid.reminderpro.views.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_note_list.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteListActivity : BaseActivity(), Observer<List<NoteEntity>>, View.OnClickListener {

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
        list?.apply {
            adapter.swapData(ArrayList(this))
        }

        if (adapter.itemCount == 0) {
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            layoutEmptyState.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fabAdd -> {
                NoteEditActivity.start(this, null)
            }
            R.id.imgRight -> {
                viewModel.add(NoteEntity(Date(), "my title", "my description"))
            }
            R.id.imgBack -> {
                finish()
            }
        }
    }
}