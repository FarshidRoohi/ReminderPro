package ir.roohi.farshid.reminderpro.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.CustomRecyclerView
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import ir.roohi.farshid.reminderpro.views.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteListActivity : BaseActivity(), Observer<MutableList<NoteEntity>>, View.OnClickListener {

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

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        recycler.adapter = adapter
        recycler.addFab(fabAdd)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        viewModel.notes.observe(this, this)

        imgRight.setOnClickListener(this)
        imgBack.setOnClickListener(this)
        fabAdd.setOnClickListener(this)

        imgRight.setImageResource(R.drawable.ic_account_circle)



        recycler.addOnScrollStateListener(object : CustomRecyclerView.OnScrollStateListener {
            override fun onScrollEnded(recyclerView: CustomRecyclerView) {
            }
        })


    }

    override fun onChanged(list: MutableList<NoteEntity>?) {
        list?.apply {
            adapter.swapData(this)
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