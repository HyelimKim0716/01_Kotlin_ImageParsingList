package com.innowireless.a01_kotlinimageparsinglist.util

import android.graphics.Bitmap
import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import org.json.JSONObject

/**
 * Created by Owner on 2017-08-23.
 */

fun JSONObject.parseImage() {
    val resultArray = getJSONArray("results")

    /* https://stackoverflow.com/questions/36184641/kotlin-iterate-through-a-jsonarray/36188796 */
    for (i in 0..(resultArray.length() -1)) {
        val result: JSONObject = resultArray.getJSONObject(i)

        resultArray.getJSONObject(i).let {
            val gender = it.getString("gender")

            val nameObject = it.getJSONObject("name")
            val nameFirst = nameObject.getString("first")
            val nameLast = nameObject.getString("last")

            val email = it.getString("email")

            val loginObject = it.getJSONObject("login")
            val username = loginObject.getString("username")
            val password = loginObject.getString("password")

            val dob = it.getString("dob")
            val cell = it.getString("cell")

            val pictureUrlObject = it.getJSONObject("picture")
            val pictureUrl = pictureUrlObject.getString("large")
            val pictureBitmap: Bitmap? = null
            val imageItem = ImageItem(gender, nameFirst, nameLast, email, username, password, dob, cell, pictureUrl, pictureBitmap)
//            imageItemList.add(imageItem)
//            imageInfoTaskCallback.onGetItem(imageItem)
            Log.d("ImageDataManager", "gender = $gender, nameFirst = $nameFirst, nameLast = $nameLast, email = $email, cell = $cell, pictureUrl = $pictureUrl")
        }
    }
}