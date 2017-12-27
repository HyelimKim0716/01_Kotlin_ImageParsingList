package com.innowireless.a01_kotlinimageparsinglist.data

import com.innowireless.a01_kotlinimageparsinglist.data.model.UserInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Owner on 2017-10-24.
 */
interface RandomUserApi {
    @GET("/api/?")
    fun getRandomUserInfo(@Query("results") result: Int) : Observable<UserInfo>
}