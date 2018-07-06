package com.test.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class TaskService {
    private var retrofit: Retrofit? = null


    /**
     * This method creates a new instance of the API interface.
     *
     * @return The API interface
     */
    val api: TaskAPI
        get() {
            val BASE_URL = "https://blog.truecaller.com/"

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build()
            }

            return retrofit!!.create(TaskAPI::class.java)
        }
}
