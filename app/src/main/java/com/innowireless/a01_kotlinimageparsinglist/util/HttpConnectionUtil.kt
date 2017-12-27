package com.innowireless.a01_kotlinimageparsinglist.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

/**
 * Created by Owner on 2017-08-22.
 */

val TAG = "HttpConnectionUtil"

interface HttpUrlConnectionCallback {
    fun onFinish(response: String)
}

fun HttpURLConnection.loadImages(callback: HttpUrlConnectionCallback) {
    Thread(Runnable { kotlin.run {
        connect()
        val response = StringBuilder()
        inputStream.bufferedReader().use {
            response.append(it.readLine())
        }

        callback.onFinish(response.toString())

        //            val inputStream: InputStream = con.getInputStream()
//            val baos = ByteArrayOutputStream()
//            val buffer = ByteArray(1024)
//            while (true) {
//                val length = inputStream.read(buffer)
//                if (length <= 0) break
//
//                baos.write(buffer, 0, length)
//            }

//            baos.flush()
//            baos.close()
//
//            val response: String = String(baos.toByteArray())
//            Log.d(TAG, "response = $response")
//
//            parseImage(response)

//            String(baos.toByteArray()).let {        // response
//                Log.d(com.innowireless.a01_kotlinimageparsinglist.util.TAG, "response = $it")
//                parseImage(it)
//            }


    } }).start()
}

fun URLConnection.getInfo() {
    connect()
    val baos = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    getInputStream().use { input ->
        baos.write(buffer, 0, input.read())
    }
}


interface Callback {
    fun onGetImageBitmap(bitmap: Bitmap)
}

class HttpConnectionUtil {


    fun getImageBitmap(url: String, callback: Callback) {
        Thread(Runnable {
            val bitmap = BitmapFactory.decodeStream(URL(url).content as InputStream)
            callback.onGetImageBitmap(bitmap)
        }).start()
    }

}