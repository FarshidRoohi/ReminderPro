package ir.roohi.farshid.reminderpro.views.adapter;


import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.databinding.DataBindingUtil;

/**
 * Android Android-Application Project at ZarinPal
 * Created by hosseinAmini on 11/3/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public abstract class BaseRecyclerAdapter<DataSetType> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataSetType> items;
    private Context           context;

    public BaseRecyclerAdapter(List<DataSetType> items) {
        this.items = items;
    }

    public BaseRecyclerAdapter() {
    }

    public abstract int getItemLayout(int viewType);

    public abstract void onBindViewHolder(ViewDataBinding viewDataBinding, int position,
                                          int viewType, DataSetType element);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (context == null) this.context = parent.getContext();

        ViewDataBinding v = DataBindingUtil.inflate
                (
                        LayoutInflater.from(parent.getContext()),
                        getItemLayout(viewType),
                        parent,
                        false
                );
        return new VH(v);

    }

    public Context getContext() {
        return this.context;
    }


    private class VH extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        private VH(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.binding = viewDataBinding;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(((VH) holder).binding, position, holder.getItemViewType(), items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public void swapData(List<DataSetType> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<DataSetType> getItems() {
        return this.items;
    }

    public void addItem(DataSetType... items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        this.items.addAll(new ArrayList<>(Arrays.asList(items)));
        notifyDataSetChanged();
    }

    public void addItem(DataSetType item, int position) {
        if (items == null) {
            items = new ArrayList<>();

        }

        if (items.size() < position) {
            position = items.size();
        } else if (position < 0) {
            position = 0;
        }

        items.add(position, item);
        notifyDataSetChanged();
    }

    public void removeItem(DataSetType... items) {
        if (this.items == null) {
            return;
        }

        this.items.removeAll(new ArrayList<>(Arrays.asList(items)));
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (items == null || position < 0 || items.size() < position) {
            return;
        }

        items.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (this.items == null) {
            return;
        }
        items.clear();
        notifyDataSetChanged();
    }

}
