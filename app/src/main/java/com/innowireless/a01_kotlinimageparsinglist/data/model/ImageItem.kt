package com.innowireless.a01_kotlinimageparsinglist.data.model

import android.graphics.Bitmap

/**
 * Created by Owner on 2017-07-27.
 */
data class ImageItem(
        val gender: String,
        val nameFirst: String,
        val nameLast: String,
        val email: String,
        val username: String,
        val password: String,
        val dob: String,        // 생년월일
        val cell: String,
        val pictureUrl: String,
        var pictureBitmap: Bitmap?
        )