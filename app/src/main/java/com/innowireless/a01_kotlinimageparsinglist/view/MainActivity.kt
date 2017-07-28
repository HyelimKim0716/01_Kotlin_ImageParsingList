package com.innowireless.a01_kotlinimageparsinglist.view

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.R
import com.innowireless.a01_kotlinimageparsinglist.util.replaceFragment
import com.innowireless.a01_kotlinimageparsinglist.view.image.ImageFragment
import kotlinx.android.synthetic.main.activity_main.*

/*
 * https://randomuser.me/api/?results=20
 * http://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * */
class MainActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

    }

    fun checkPermission() {
        val permissionInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        Log.d("MainActivity", "checkPermission internet = $permissionInternet (0 : Granted, -1 : Denied)")
        if (permissionInternet == PackageManager.PERMISSION_GRANTED) {
            PackageManager.PERMISSION_DENIED
            replaceFragment(frame_layout.id, ImageFragment.newInstance())
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    replaceFragment(frame_layout.id, ImageFragment.newInstance())
                }
            }
        }
    }
}
