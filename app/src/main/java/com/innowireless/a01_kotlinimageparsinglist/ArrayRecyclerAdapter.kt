package com.innowireless.a01_kotlinimageparsinglist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.AdapterView

/**
 * Created by Owner on 2017-07-27.
 */
abstract class ArrayRecyclerAdapter<ITEM, VIEW_TYPE : RecyclerView.ViewHolder> (val context: Context) :
        RecyclerView.Adapter<VIEW_TYPE>(), BaseRecyclerModel<ITEM>, BaseRecyclerView {

    override var onItemClickListener: AdapterView.OnItemClickListener? = null


}