package ir.roohi.farshid.reminderpro.views.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_note_edit.*
import org.jetbrains.annotations.Nullable
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteEditActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: NoteViewModel

    companion object {
        private var noteEntity: NoteEntity? = null

        fun start(context: Context, @Nullable note: NoteEntity?) {
            val intent = Intent(context, NoteEditActivity::class.java)
            noteEntity = note
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        imgBack.setOnClickListener(this)
        this.viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)


        if (noteEntity != null) {
            edtText.setText(noteEntity!!.text)
            return
        }

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> {
                this.save()
                finish()
            }
        }

    }

    override fun onBackPressed() {
        this.save()
        super.onBackPressed()
    }

    private fun save() {
        val text = edtText.text.toString()

        if (text.isEmpty() && noteEntity == null) {
            return
        }

        if (noteEntity == null) {
            this.viewModel.add(text)

        } else if (noteEntity!!.text != text) {
            noteEntity!!.date = Date()
            noteEntity!!.text = text
            this.viewModel.updateNote(noteEntity!!)

        }
    }
}