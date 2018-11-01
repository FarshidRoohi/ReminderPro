package ir.roohi.farshid.reminderpro.customViews

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import ir.roohi.farshid.reminderpro.R

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class CustomRecyclerView : RecyclerView {

    private var listener: OnScrollStateListener? = null


    private var scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val totalItemCount = this@CustomRecyclerView.adapter!!.itemCount
            val lastItemDidVisible = (this@CustomRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 1

            if (totalItemCount == lastItemDidVisible) {
                listener!!.onScrollEnded(this@CustomRecyclerView)
            }

        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.initAttrs(attrs)
    }


    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        this.initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView)
        val showDivider = typedArray.getBoolean(R.styleable.CustomRecyclerView_show_divider, true)
        typedArray.recycle()

        if (showDivider) {
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            // add shape to divider
            // itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
            this.addItemDecoration(itemDecoration)
        }
    }


    fun addOnScrollStateListener(listener: OnScrollStateListener) {
        this.listener = listener
        super.addOnScrollListener(scrollListener)
    }


    interface OnScrollStateListener {
        fun onScrollEnded(recyclerView: CustomRecyclerView)
    }
}
