package com.innowireless.a01_kotlinimageparsinglist.view.image.presenter

import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem

/**
 * Created by Owner on 2017-07-27.
 */
interface ImageContract {

    interface View {
        fun addItems(imageItem: ImageItem)

        fun notifyDataSetChanged()
    }

    interface Presenter {
        val view: View

        fun loadImages()

        fun showImages()
    }
}