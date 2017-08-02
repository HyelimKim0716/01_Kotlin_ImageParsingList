package com.innowireless.a01_kotlinimageparsinglist.view.image.presenter

import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.data.ImageData
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL

/**
 * Created by Owner on 2017-07-27.
 */
class ImagePresenter(override val view: ImageContract.View) : ImageContract.Presenter{
    private val TAG = "ImagePresenter"

    var loadImageThread: Thread? = null
    var imageItemList: MutableList<ImageItem> = ArrayList()

    override fun loadImages() {
        imageItemList.clear()
        if (loadImageThread != null) {
            loadImageThread!!.interrupt()
            loadImageThread = null
        }

        // Koroutine
        loadImageThread = Thread(Runnable { kotlin.run {
            val imgUrl = URL("https://randomuser.me/api/?results=20")
            val con = imgUrl.openConnection()
            con.connect()

            Log.d(TAG, "URL = $imgUrl")
            val inputStream: InputStream = con.getInputStream()
            val baos: ByteArrayOutputStream= ByteArrayOutputStream()
            val buffer = ByteArray(1024)

            while (true) {
                val length = inputStream.read(buffer)
                if (length <= 0) break

                baos.write(buffer, 0, length)
            }

            baos.flush()
            baos.close()

//            val response: String = baos.toByteArray().toString()
            val response: String = String(baos.toByteArray())
            Log.d(TAG, "response = $response")

            val imageData = ImageData()
            imageData.imageDataListener = object : ImageData.ImageDataListener {
                override fun onGetPictureBitmap() {
                    imageItemList = imageData.imageItemList
                    Log.d(TAG, "onGetPictureBitmap ${imageItemList.size}")
                    showImages()
                }
            }

            imageData.parseImage(response)

        } })

        loadImageThread!!.start()
    }

    override fun showImages() {
        Log.d(TAG, "showImages")
        imageItemList.forEach {
            Log.d(TAG, "showImages, it = $it")
            view.addItems(it)
        }

        view.notifyDataSetChanged()
    }

}


