package com.innowireless.a01_kotlinimageparsinglist

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

/**
 * Created by Owner on 2017-07-27.
 */
abstract class BaseViewHolder<ITEM> (val adapter: RecyclerView.Adapter<*>, itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(@LayoutRes layoutRes: Int, parent: ViewGroup?, adapter: RecyclerView.Adapter<*>)
    : this(adapter, LayoutInflater.from((adapter as? ArrayRecyclerAdapter<*, *>)?.context).inflate(layoutRes, parent, false))

    abstract fun onBindViewHolder(item: ITEM?, position: Int)

    protected val context: Context?
        get() = (adapter as? ArrayRecyclerAdapter<*, *>)?.context

    protected val onItemClick: AdapterView.OnItemClickListener?
    get() = (adapter as? ArrayRecyclerAdapter<*, *>)?.onItemClickListener

    protected val onItemLongClick: AdapterView.OnItemLongClickListener?
    get() = (adapter as? ArrayRecyclerAdapter<*,*>)?.onItemLongClickListener
}