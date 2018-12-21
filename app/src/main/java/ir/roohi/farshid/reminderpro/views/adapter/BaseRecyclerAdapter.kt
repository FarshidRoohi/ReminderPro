package ir.roohi.farshid.reminderpro.views.adapter


import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList
import java.util.Arrays

import android.databinding.DataBindingUtil

/**
 * Android Android-Application Project at ZarinPal
 * Created by hosseinAmini on 11/3/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

abstract class BaseRecyclerAdapter<DataSetType> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var items: MutableList<DataSetType>? = null
    var context: Context? = null
        private set

    private constructor(items: ArrayList<DataSetType>) {
        this.items = items
    }

    constructor()

    abstract fun getItemLayout(viewType: Int): Int

    abstract fun onBindViewHolder(viewDataBinding: ViewDataBinding, position: Int,
                                  viewType: Int, element: DataSetType)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (context == null) this.context = parent.context

        val v = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                getItemLayout(viewType),
                parent,
                false
        )
        return VH(v)

    }


    private inner class VH constructor(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder((holder as BaseRecyclerAdapter<*>.VH).binding, position, holder.getItemViewType(), items!![position])
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }


    fun swapData(items: MutableList<DataSetType>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItems(): List<DataSetType>? {
        return this.items
    }

    fun addItem(vararg items: DataSetType) {
        if (this.items == null) {
            this.items = ArrayList()
        }

        this.items!!.addAll(ArrayList(Arrays.asList(*items)))
        notifyDataSetChanged()
    }

    fun addItem(item: DataSetType, position: Int) {
        var position = position
        if (items == null) {
            items = ArrayList()

        }

        if (items!!.size < position) {
            position = items!!.size
        } else if (position < 0) {
            position = 0
        }

        items!!.add(position, item)
        notifyDataSetChanged()
    }

    fun removeItem(vararg items: DataSetType) {
        if (this.items == null) {
            return
        }

        this.items!!.removeAll(ArrayList(Arrays.asList(*items)))
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (items == null || position < 0 || items!!.size < position) {
            return
        }

        items!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeAll() {
        if (this.items == null) {
            return
        }
        items!!.clear()
        notifyDataSetChanged()
    }

}