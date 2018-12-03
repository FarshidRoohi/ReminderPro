package ir.roohi.farshid.reminderpro.views.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_note_edit.*
import kotlinx.android.synthetic.main.toolbar.*
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
        imgRight.setImageResource(R.drawable.ic_delete)
        imgRight.setOnClickListener(this)
        this.viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)


        if (noteEntity != null) {
            edtText.setText(noteEntity!!.text)
            txtTitle.text = noteEntity!!.title
            return
        }

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> {
                this.save()
                finish()
            }
            R.id.imgRight -> {
                this.remove()
            }
        }

    }

    override fun onBackPressed() {
        this.save()
        super.onBackPressed()
    }

    private fun remove() {
        if (noteEntity == null) {
            return
        }
        val alertDialog = AlertDialog.Builder(supportFragmentManager,
                getString(R.string.note), getString(R.string.sure_note_delete))
        alertDialog.setBtnNegative(getString(R.string.no), View.OnClickListener {
            alertDialog.dialog!!.dismiss()
        })
        alertDialog.setBtnPositive(getString(R.string.yes), View.OnClickListener {
            this.viewModel.removeNote(noteEntity)
            finish()
        })
        alertDialog.build().show()
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
        Toast.makeText(this, getString(R.string.save), Toast.LENGTH_SHORT).show()
    }
}