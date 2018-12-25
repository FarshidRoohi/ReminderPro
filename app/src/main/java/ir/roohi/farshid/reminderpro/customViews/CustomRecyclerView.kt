package ir.roohi.farshid.reminderpro.customViews

import android.content.Context
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import ir.roohi.farshid.reminderpro.R

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class CustomRecyclerView : androidx.recyclerview.widget.RecyclerView {

    private var listener: OnScrollStateListener? = null
    private var fab: FloatingActionButton? = null


    private var scrollListener: androidx.recyclerview.widget.RecyclerView.OnScrollListener = object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {

            if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE) {
                fab!!.show()
            }

            super.onScrollStateChanged(recyclerView, newState)

            val totalItemCount = this@CustomRecyclerView.adapter!!.itemCount
            val lastItemDidVisible = (this@CustomRecyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition() + 1

            if (totalItemCount == lastItemDidVisible) {
                listener!!.onScrollEnded(this@CustomRecyclerView)
            }


        }

        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
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
            val itemDecoration = androidx.recyclerview.widget.DividerItemDecoration(context, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL)
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
