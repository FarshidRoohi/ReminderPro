package ir.roohi.farshid.reminderpro.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import ir.roohi.farshid.reminderpro.views.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_list_note.*
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class ListNoteActivity : BaseActivity(), Observer<List<NoteEntity>> {

    private lateinit var adapter: NoteAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ListNoteActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_note)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()

        recycler.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        viewModel.notes.observe(this, this)

        imgProfile.setOnClickListener {
            viewModel.add(NoteEntity(Date() , "my title","my description"))
        }


    }

    override fun onChanged(list: List<NoteEntity>?) {
        adapter.swapData(list)
    }
}