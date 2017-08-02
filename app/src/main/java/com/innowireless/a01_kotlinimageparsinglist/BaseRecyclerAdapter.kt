package com.innowireless.a01_kotlinimageparsinglist

import android.content.Context

/**
 * Created by Owner on 2017-07-27.
 */
abstract class BaseRecyclerAdapter<ITEM>(context: Context) : ArrayRecyclerAdapter<ITEM, BaseViewHolder<ITEM>>(context) {

    override fun onBindViewHolder(holder: BaseViewHolder<ITEM>?, position: Int) {
        holder?.onBindViewHolder(getItem(position), position)
    }
}