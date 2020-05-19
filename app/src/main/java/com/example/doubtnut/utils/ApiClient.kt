package com.example.doubtnut.utils

import android.provider.SyncStateContract
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        fun getClient() : Retrofit {
            val logging= HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient=  OkHttpClient.Builder().addInterceptor(logging).build()
            val base_url= Constant.BASE_URL
            return Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient).build()

        }
    }

}


