package com.innowireless.a01_kotlinimageparsinglist

import android.support.v7.widget.RecyclerView
import android.widget.AdapterView

/**
 * Created by Owner on 2017-07-27.
 */
interface BaseRecyclerView {
    var onItemClickListener: AdapterView.OnItemClickListener?

    fun setOnItemClickListener(listener: (RecyclerView.Adapter<*>, Int) -> Unit)

    var onItemLongClickListener: AdapterView.OnItemLongClickListener?

    fun setOnItemLongClickListener(listner: (RecyclerView.Adapter<*>, Int) -> Boolean)

    fun notifyDataSetChanged()
}