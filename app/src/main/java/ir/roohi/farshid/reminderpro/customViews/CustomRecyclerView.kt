package ir.roohi.farshid.reminderpro.customViews

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import ir.roohi.farshid.reminderpro.R

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class CustomRecyclerView : RecyclerView {

    private var listener: OnScrollStateListener? = null
    private var fab: FloatingActionButton? = null


    private var scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                fab!!.show()
            }

            super.onScrollStateChanged(recyclerView, newState)

            val totalItemCount = this@CustomRecyclerView.adapter!!.itemCount
            val lastItemDidVisible = (this@CustomRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 1

            if (totalItemCount == lastItemDidVisible) {
                listener!!.onScrollEnded(this@CustomRecyclerView)
            }


        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 || dy < 0 && fab!!.isShown) {
                fab!!.hide()
            }
        }
    }

    constructor(context: Context) : super(context)

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

    fun addFab(fab: FloatingActionButton) {
        this.fab = fab
    }


    interface OnScrollStateListener {
        fun onScrollEnded(recyclerView: CustomRecyclerView)
    }
}
