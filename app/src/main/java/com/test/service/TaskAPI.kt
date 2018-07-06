package com.test.service

import io.reactivex.Observable
import retrofit2.http.GET

interface TaskAPI {

    @GET("2018/01/22/life-as-an-android-engineer/")
    fun fetchAnswer1(): Observable<String>

    @GET("2018/01/22/life-as-an-android-engineer/")
    fun fetchAnswer2(): Observable<String>

    @GET("2018/01/22/life-as-an-android-engineer/")
    fun fetchAnswer3(): Observable<String>
}
