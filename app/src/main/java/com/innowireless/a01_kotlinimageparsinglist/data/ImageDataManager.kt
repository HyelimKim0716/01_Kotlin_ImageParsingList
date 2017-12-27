package com.innowireless.a01_kotlinimageparsinglist.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.AsyncTask
import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Owner on 2017-07-28.
 */

class GetImageInfoTask : AsyncTask<String, Int, String>() {
    val TAG = "ImageDataManager"
    interface ImageInfoTaskCallback {
        fun onGetItem(item: ImageItem)
        fun onGetItemList(itemList: List<ImageItem>)
    }

    lateinit var imageInfoTaskCallback: ImageInfoTaskCallback

    override fun doInBackground(vararg url: String?): String {
        val response = StringBuilder()

        (URL(url[0]).openConnection() as HttpURLConnection).let {
            it.connect()

            it.inputStream.bufferedReader().use {
                response.append(it.readLine())
            }
        }

        return response.toString()
    }

    override fun onPostExecute(result: String?) {
        if (result != null)
            imageInfoTaskCallback.onGetItemList(parseImage(result))
    }

    fun parseImage(response: String) : ArrayList<ImageItem> {
        val imageItemList = ArrayList<ImageItem>()
        val resultArray = JSONObject(response).getJSONArray("results")  // results, info

        /* https://stackoverflow.com/questions/36184641/kotlin-iterate-through-a-jsonarray/36188796 */
        for (i in 0..(resultArray.length() -1)) {

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
                val pictureUrl = pictureUrlObject.getString("medium")
                val pictureBitmap: Bitmap? = null
                val imageItem = ImageItem(gender, nameFirst, nameLast, email, username, password, dob, cell, pictureUrl, pictureBitmap)
                imageItemList.add(imageItem)
//                imageInfoTaskCallback.onGetItem(imageItem)
                Log.d("ImageDataManager", "gender = $gender, nameFirst = $nameFirst, nameLast = $nameLast, email = $email, cell = $cell, pictureUrl = $pictureUrl")
            }
        }

        return imageItemList
    }
}

class GetImageBitmapTask : AsyncTask<String, Int, Bitmap>() {
    val TAG = "ImageDataManager"
    interface ImageBitmapTaskCallback {
        fun onGetImageBitmap(bitmap: Bitmap)
    }

    lateinit var imageBitmapTaskCallback: ImageBitmapTaskCallback

    override fun doInBackground(vararg url: String?): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
//        var bitmap: Bitmap? = BitmapFactory.decodeStream(URL(url[0]).openConnection().content as InputStream, null, options)
        var bitmap: Bitmap? = BitmapFactory.decodeStream(URL(url[0]).openConnection().content as InputStream)
        val scaleWidth = 1080F/ bitmap?.width!!
        val scaleHeight = 1080F/bitmap?.height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)


//        val resized = resizeBitmap(url[0], bitmap, options)
        val resized = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)

        if (bitmap != resized)
            bitmap?.recycle()

        return resized
    }

    override fun onPostExecute(result: Bitmap?) {
        if (result != null) {
            imageBitmapTaskCallback.onGetImageBitmap(result)
        }
    }

    fun resizeBitmap(url: String?, bitmap: Bitmap?, options: BitmapFactory.Options) : Bitmap {
        var scale = 0F
        var imageWith = 0
        var imageHeight = 0
        var width = 0F
        var height = 0F

        options.run {
            Log.e(TAG, "outWith = $outWidth, outHeight = $outHeight")
            imageWith = outWidth
            imageHeight = outHeight
            val scaleX = 1080F / outWidth
            val scaleY = 1080F / outHeight
            scale = if (scaleX < scaleY) scaleX else scaleY
            if (scale > 1) {
                scale = 1F
            } else {
                inSampleSize = Math.round(1F/scale)
                inPurgeable = true
                inDither = true
            }

            inJustDecodeBounds = false
        }

        width = scale * imageWith
        height = scale * imageHeight

        Log.e(TAG, "width = $width, height = $height, imageWidth = $imageWith, imageHeight = $imageHeight, scale = $scale")

        val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().content as InputStream, null, options)

        val matrix = Matrix()

        // resize
//        matrix.postScale(width/bitmap.width, height/bitmap.height)
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

}