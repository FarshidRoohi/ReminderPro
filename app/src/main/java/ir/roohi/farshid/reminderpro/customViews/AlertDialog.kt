package ir.roohi.farshid.reminderpro.customViews


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import ir.roohi.farshid.reminderpro.R

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/3/18.
 */
@SuppressLint("ValidFragment")
class AlertDialog private constructor(private var fm: FragmentManager, private val builder: Builder) : DialogFragment() {
    private var layoutAllContent: ViewGroup? = null
    private var imgDialog: ImageView? = null
    private var btnPositive: Button? = null
    private var btnNegative: Button? = null
    private var btnSkip: Button? = null
    private var chkNotShowAgain: CheckBox? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, container)

        this.layoutAllContent = view.findViewById(R.id.layout_all_content)
        this.imgDialog = view.findViewById(R.id.img_dialog)
        this.btnPositive = view.findViewById(R.id.btn_positive)
        this.btnNegative = view.findViewById(R.id.btn_negative)
        this.btnSkip = view.findViewById(R.id.btn_skip)
        this.chkNotShowAgain = view.findViewById(R.id.chk_not_show_again)

        val txtTitle = view.findViewById<TextView>(R.id.txt_title)
        val txtDesc = view.findViewById<TextView>(R.id.txt_desc)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        txtTitle.text = this.builder.title
        txtDesc.text = this.builder.desc

        this.setPositiveButton(this.builder.btnPositiveTitle, this.builder.btnPositiveListener)
        this.setNegativeButton(this.builder.btnNegativeTitle, this.builder.btnNegativeListener)
        this.setSkipButton(this.builder.btnThirdTitle, this.builder.btnThirdListener)
        this.setCheckBox(this.builder.chkTitle, this.builder.chkChangeListener)
        this.setButtonColor(this.builder.colorButton)
        this.setBackgroundColor(this.builder.colorBackground)
        this.setIcon(this.builder.icon)


        return view
    }

    private fun setPositiveButton(title: String?, listener: View.OnClickListener?) {
        this.btnPositive!!.visibility = View.VISIBLE
        if (listener == null) {
            this.btnPositive!!.text = getString(R.string.ok)
            this.btnPositive!!.setOnClickListener { dismiss() }
            return
        }

        this.btnPositive!!.text = title
        this.btnPositive!!.setOnClickListener(listener)
    }


    private fun setNegativeButton(title: String?, listener: View.OnClickListener?) {
        if (listener == null) {
            return
        }
        this.btnNegative!!.visibility = View.VISIBLE
        this.btnNegative!!.text = title
        this.btnNegative!!.setOnClickListener(listener)
    }

    private fun setSkipButton(title: String?, listener: View.OnClickListener?) {

        if (listener == null) {
            return
        }

        this.btnSkip!!.visibility = View.VISIBLE
        this.btnSkip!!.text = title
        this.btnSkip!!.setOnClickListener(listener)
    }

    private fun setCheckBox(title: String?, listener: CompoundButton.OnCheckedChangeListener?) {

        if (listener == null) {
            return
        }
        this.chkNotShowAgain!!.visibility = View.VISIBLE
        this.chkNotShowAgain!!.text = title
        this.chkNotShowAgain!!.setOnCheckedChangeListener(listener)
    }

    private fun setIcon(@DrawableRes icon: Int) {
        if (icon == 0) {
            this.imgDialog!!.visibility = View.GONE
            return
        }
        this.imgDialog!!.setImageResource(icon)

    }

    private fun setButtonColor(@ColorInt color: Int) {
        var color = color
        if (color == 0) {
            color = Color.DKGRAY
        }
        this.chkNotShowAgain!!.setTextColor(color)
        this.btnSkip!!.setTextColor(color)
        this.btnNegative!!.setTextColor(color)
        this.btnPositive!!.setTextColor(color)

    }

    private fun setBackgroundColor(@ColorInt color: Int) {
        var color = color
        if (color == 0) {
            color = Color.WHITE
        }
        this.layoutAllContent!!.setBackgroundColor(color)
    }

    fun show() {
        super.show(this.fm, javaClass.name)
    }

    class Builder(private val fm: FragmentManager, var title: String?, var desc: String?) {

        var dialog: AlertDialog? = null
            private set
        var isCancelable: Boolean = false


        //region Button Negative
        var btnNegativeTitle: String? = null
            private set
        var btnNegativeListener: View.OnClickListener? = null
            private set
        //endRegion

        //region Button Positive
        var btnPositiveTitle: String? = null
            private set
        var btnPositiveListener: View.OnClickListener? = null
            private set
        //endRegion

        //region Third Button
        var btnThirdTitle: String? = null
            private set
        var btnThirdListener: View.OnClickListener? = null
            private set
        //endRegion

        //region CheckBox
        var chkTitle: String? = null
            private set
        var chkChangeListener: CompoundButton.OnCheckedChangeListener? = null
            private set
        //endRegion


        @DrawableRes
        var icon: Int = 0
        @ColorInt
        var colorButton: Int = 0
        @ColorInt
        var colorBackground: Int = 0

        fun setBtnNegative(title: String, listener: View.OnClickListener) {
            this.btnNegativeTitle = title
            this.btnNegativeListener = listener
        }

        fun setBtnPositive(title: String, listener: View.OnClickListener) {
            this.btnPositiveTitle = title
            this.btnPositiveListener = listener
        }

        fun setBtnThird(title: String, listener: View.OnClickListener) {
            this.btnThirdTitle = title
            this.btnThirdListener = listener
        }

        fun setCheckBox(title: String, listener: CompoundButton.OnCheckedChangeListener) {
            this.chkTitle = title
            this.chkChangeListener = listener
        }

        fun build(): AlertDialog {
            this.dialog = AlertDialog(this.fm, this)
            return this.dialog!!

        }
    }

}
