package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.model.NoteEntity
import kotlinx.android.synthetic.main.activity_note_edit.*
import org.jetbrains.annotations.Nullable

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteEditActivity : BaseActivity(), View.OnClickListener {


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

        if (noteEntity != null) {
            edtText.setText(noteEntity!!.text)
            return
        }


    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}