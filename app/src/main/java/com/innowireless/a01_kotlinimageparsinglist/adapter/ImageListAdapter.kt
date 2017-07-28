package com.innowireless.a01_kotlinimageparsinglist.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import com.innowireless.a01_kotlinimageparsinglist.R
import kotlinx.android.synthetic.main.image_list_item.view.*
import java.io.InputStream
import java.net.URL

/**
 * Created by Owner on 2017-07-27.
 */
class ImageListAdapter(val context: Context) : RecyclerView.Adapter<ImageListAdapter.ImageListAdapterViewHolder>() {
    val itemList: MutableList<ImageItem> = ArrayList()

    fun addItem(item: ImageItem) {
        itemList.add(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageListAdapterViewHolder {
        return ImageListAdapterViewHolder(context, parent)
    }

    override fun onBindViewHolder(holder: ImageListAdapterViewHolder?, position: Int) {
        holder?.onBindView(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getItem(position: Int) = itemList[position]

    inner class ImageListAdapterViewHolder(context: Context, parent: ViewGroup?)
        : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)) {

        fun onBindView(position: Int) {
            with(itemView) {
                tv_name.text = "${itemList[position].nameFirst} ${itemList[position].nameLast}"

//                val url = URL(itemList[position].pictureUrl)
                iv_image.setImageBitmap(itemList[position].pictureBitmap)
            }
        }
    }
}