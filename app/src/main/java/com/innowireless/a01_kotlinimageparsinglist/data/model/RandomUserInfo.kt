package com.innowireless.a01_kotlinimageparsinglist.data.model

import android.graphics.Bitmap

/**
 * Created by Owner on 2017-11-08.
 */

data class UserInfo(
        val results: ArrayList<Result>,
        val info: Info
)

data class Result (
        val gender: String,
        val name: Name,
        val location: Location,
        val email: String,
        val login: Login,
        val dob: String,
        val registered: String,
        val phone: String,
        val cell: String,
        val id: Id,
        val picture: Picture,
        val nat: String
)

data class Name (
        val title: String,
        val first: String,
        val last: String
)

data class Location (
        val street: String,
        val city: String,
        val state: String,
        val postcode: String
)

data class Login (
        val userName: String,
        val password: String,
        val salt: String,
        val md5: String,
        val sha1: String,
        val sha256: String
)

data class Id (
        val name: String,
        val value: String
)

data class Picture (
        val large: String,
        val medium: String,
        val thumbnail: String,
        var pictureBitmap: Bitmap
)

data class Info (
        val seed: String,
        val results: Int,
        val page: Int,
        val version: String
)