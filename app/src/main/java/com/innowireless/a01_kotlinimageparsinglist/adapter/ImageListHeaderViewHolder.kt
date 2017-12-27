package com.innowireless.a01_kotlinimageparsinglist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innowireless.a01_kotlinimageparsinglist.R

/**
 * Created by Owner on 2017-11-07.
 */
class ImageListHeaderViewHolder(context: Context, parent: ViewGroup?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_list_header, parent, false)) {

    fun onBindView() {
        itemView.setOnClickListener({
            itemView.setBackgroundResource(android.R.color.holo_blue_bright)
        })
    }
}