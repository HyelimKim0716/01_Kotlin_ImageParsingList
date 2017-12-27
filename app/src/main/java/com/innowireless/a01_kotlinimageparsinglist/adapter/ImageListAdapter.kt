package com.innowireless.a01_kotlinimageparsinglist.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import com.innowireless.a01_kotlinimageparsinglist.R
import com.innowireless.a01_kotlinimageparsinglist.data.GetImageBitmapTask
import kotlinx.android.synthetic.main.image_list_item.view.*

/**
 * Created by Owner on 2017-07-27.
 *
 * https://medium.com/@programmerasi/how-to-implement-load-more-in-recyclerview-3c6358297f4
 */
class ImageListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "ImageListAdapter"
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_HEADER = Int.MIN_VALUE
    private var TYPE_FOOTER = Int.MAX_VALUE

    val itemList: MutableList<ImageItem> = ArrayList()

    fun addItem(item: ImageItem) {
        itemList.add(item)
        TYPE_FOOTER = itemList.size + 1
    }

    override fun getItemViewType(position: Int): Int = when(position) {
        0 -> VIEW_TYPE_HEADER
        itemList.size + 1 -> VIEW_TYPE_LOADING
        else -> VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        VIEW_TYPE_HEADER -> ImageListHeaderViewHolder(context, parent)
        VIEW_TYPE_ITEM -> ImageListAdapterViewHolder(context, parent)
        else -> ImageListLoadingViewHolder(context, parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is ImageListAdapterViewHolder -> holder.onBindView(position - 1)
            is ImageListHeaderViewHolder -> holder.onBindView()
        }
    }

    override fun getItemCount(): Int {
        if (itemList.isEmpty()) return -1
        return itemList.size + 2
    }

    fun getItem(position: Int) = itemList[position - 1]

    inner class ImageListAdapterViewHolder(context: Context, parent: ViewGroup?)
        : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)) {

        fun onBindView(position: Int) {
            if (itemList.isEmpty()) return
            Log.d(TAG, "onBindView, position = $position")
            with(itemView) {
                tv_name.text = "$position ${itemList[position].nameFirst} ${itemList[position].nameLast}"

                if (itemList[position].pictureBitmap == null) {
                    Log.d("ImageListAdapter", "$position picture is null")
                    var mBitmap: Bitmap
                    val imageBitmapTask = GetImageBitmapTask()
                    imageBitmapTask.imageBitmapTaskCallback = object : GetImageBitmapTask.ImageBitmapTaskCallback {
                        override fun onGetImageBitmap(bitmap: Bitmap) {
                            itemList[position].pictureBitmap = bitmap
                            mBitmap = bitmap
                            Log.d("ImageListAdapter", "Get Image bitmap, position = $position")
                            iv_image.setImageBitmap(mBitmap)
//                            bitmap.recycle()
//                            }
                        }
                    }

                    imageBitmapTask.execute(itemList[position].pictureUrl)
                } else {
                    Log.d("ImageListAdapter", "$position picture is NOT null")
                    iv_image.setImageBitmap(itemList[position].pictureBitmap)
                }

                val info: String =
                        """Name : ${itemList[position].nameFirst} ${itemList[position].nameLast}
                                |User Name : ${itemList[position].username}
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