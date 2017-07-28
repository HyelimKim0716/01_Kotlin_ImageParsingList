package com.innowireless.a01_kotlinimageparsinglist.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import org.json.JSONObject
import java.io.InputStream
import java.net.URL

/**
 * Created by Owner on 2017-07-28.
 */
class ImageData {

    var imageItemList = ArrayList<ImageItem>()
    interface ImageDataListener {
        fun onGetPictureBitmap()
    }

    lateinit var imageDataListener: ImageDataListener
    fun parseImage(response: String) {
        imageItemList.clear()

        val job = JSONObject(response)      // result, info
        val resultArray = job.getJSONArray("results")

        /* https://stackoverflow.com/questions/36184641/kotlin-iterate-through-a-jsonarray/36188796 */
        for (i in 0..(resultArray.length() -1)) {
            val result: JSONObject = resultArray.getJSONObject(i)

            val gender = result.getString("gender")
            val nameObject = result.getJSONObject("name")
            val nameFirst = nameObject.getString("first")
            val nameLast = nameObject.getString("last")
            val email = result.getString("email")
            val loginObject = result.getJSONObject("login")
            val username = loginObject.getString("username")
            val password = loginObject.getString("password")
            val dob = result.getString("dob")
            val cell = result.getString("cell")
            val pictureUrlObject = result.getJSONObject("picture")
            val pictureUrl = pictureUrlObject.getString("large")
            val pictureBitmap: Bitmap? = null
            val imageItem: ImageItem = ImageItem(gender, nameFirst, nameLast, email, username, password, dob, cell, pictureUrl, pictureBitmap)
            imageItemList.add(imageItem)
            Log.d("ImageData", "gender = $gender, nameFirst = $nameFirst, nameLast = $nameLast, email = $email, cell = $cell, pictureUrl = $pictureUrl")
        }

        parsePictureBitmap()

    }

    fun parsePictureBitmap() {
        Thread(Runnable {
            for (item in imageItemList) {
                item.pictureBitmap = BitmapFactory.decodeStream(URL(item.pictureUrl).content as InputStream)
            }

            imageDataListener.onGetPictureBitmap()
        }).start()
    }



}