package com.innowireless.a01_kotlinimageparsinglist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.innowireless.a01_kotlinimageparsinglist.R
import com.innowireless.a01_kotlinimageparsinglist.data.model.Result
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.image_list_item.view.*

/**
 * Created by Owner on 2017-11-07.
 */

class ImageResultListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "ImageListAdapter"
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_HEADER = Int.MIN_VALUE
    private var mTypeFooter = Int.MAX_VALUE

    val itemList: ArrayList<Result> = ArrayList()

    fun addItem(item: Result) {
        itemList.add(item)

    }

    override fun getItemViewType(position: Int): Int = when(position) {
        0 -> VIEW_TYPE_HEADER
        itemList.size + 1 -> VIEW_TYPE_LOADING
        else -> VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is ImageListAdapterViewHolder -> holder.onBindView(position - 1)
            is ImageListHeaderViewHolder -> holder.onBindView()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
            = when (viewType) {
        VIEW_TYPE_HEADER -> ImageListHeaderViewHolder(context, parent)
        VIEW_TYPE_ITEM -> ImageListAdapterViewHolder(context, parent)
        else -> ImageListLoadingViewHolder(context, parent)
    }

    override fun getItemCount(): Int {
        if (itemList.isEmpty()) return -1
        return itemList.size + 2
    }

    inner class ImageListAdapterViewHolder(context: Context, parent: ViewGroup?)
        : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)) {

        fun onBindView(position: Int) {
            if (itemList.isEmpty()) return
            Log.d(TAG, "onBindView, position = $position")

//            Flowable.fromIterable(itemList)
//                    .subscribe({
//                        println("next = " + it)
//                        mTypeFooter = itemList.size + 1
//                    })

            itemList.toObservable().subscribe({
                println("next = " + it)
                mTypeFooter = itemList.size + 1
            })

            with(itemView) {
                tv_name.text = "$position ${itemList[position].name.first} ${itemList[position].name.last}"
                Glide.with(context)
                        .load(itemList[position].picture.large)
                        .into(iv_image)

                val info: String =
                        """Name : ${itemList[position].name.first} ${itemList[position].name.last}
                                |User Name : ${itemList[position].id.name}
                                |Email : ${itemList[position].email}
                                |Birth : ${itemList[position].gender}""".trimMargin()

                tv_info.text = info
                tv_info.visibility = View.GONE
                itemView.setOnClickListener({
                    if (tv_name.visibility == View.VISIBLE && iv_image.visibility == View.VISIBLE) {
                        tv_name.visibility = View.GONE
                        iv_image.visibility = View.GONE
                        tv_info.visibility = View.VISIBLE
                    } else {
                        tv_name.visibility = View.VISIBLE
                        iv_image.visibility = View.VISIBLE
                        tv_info.visibility = View.GONE
                    }
                })
            }
        }
    }

}