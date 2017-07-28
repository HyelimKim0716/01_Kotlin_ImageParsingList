package com.innowireless.a01_kotlinimageparsinglist.util

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by Owner on 2017-07-27.
 */
fun AppCompatActivity.replaceFragment(fragmentId: Int, fragment: Fragment){
    val transaction = this.supportFragmentManager.beginTransaction()
    transaction.replace(fragmentId, fragment)
    transaction.commitAllowingStateLoss()
}