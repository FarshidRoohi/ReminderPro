package ir.roohi.farshid.reminderpro.views.activities

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_note_edit.*
import org.jetbrains.annotations.Nullable
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteEditActivity : BaseActivity() {

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
        this.viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        this.toolbar.setIconLeftListener(View.OnClickListener {
            this.toolbar.getLeftImageView().isEnabled = false
            this.save()
            finish()
        })
        this.toolbar.setIconRightListener(View.OnClickListener {
            this.remove()
        })

        if (noteEntity != null) {
            edtText.setText(noteEntity!!.text)
            this.toolbar.setCaption(noteEntity!!.title!!)
            return
        }

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
            this.viewModel.removeNote(noteEntity!!)
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
        showMsg(getString(R.string.save))
    }

    override fun onBackPressed() {
        this.save()
        super.onBackPressed()
    }

}