package com.example.doubtnut.utils

import com.example.doubtnut.responsemodel.HeadlineModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {


    @Headers("X-Api-Key:334d582ec3f340a5a60ed696395ed500"
      ,"content-type:application/json")
    @GET("v2/top-headlines")
    fun getHeadlineList(@Query("country")  country:String): Observable<HeadlineModel>
}

