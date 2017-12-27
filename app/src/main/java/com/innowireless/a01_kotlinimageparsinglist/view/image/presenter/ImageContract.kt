package com.innowireless.a01_kotlinimageparsinglist.view.image.presenter

import android.support.v7.widget.GridLayoutManager
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import com.innowireless.a01_kotlinimageparsinglist.data.model.Result

/**
 * Created by Owner on 2017-07-27.
 */
interface ImageContract {

    interface View {
        val mLayoutManager: GridLayoutManager

        fun addItems(imageItem: ImageItem)

        fun addItem(result: Result)

        fun notifyDataSetChanged()
    }

    interface Presenter {
        val view: View

        fun loadImagesWithRxJavaRetrofit()

        fun loadImagesWithRxJava()

        fun loadImagesWithAsyncTask()

        fun showImages()
        fun checkPassword(password: String): Boolean
    }
}