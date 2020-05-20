package com.example.doubtnut.utils

import android.content.Context
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        fun getClient(context: Context): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpCacheDirectory = File(context.getCacheDir(), "offlineCache")

            //10 MB
            val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

            val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(responseInterceptor())
                .addInterceptor(responseInterceptorOffline(context))
                .addInterceptor(logging).build()


            val base_url = Constant.BASE_URL
            return Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient).build()

        }

        fun responseInterceptor(): Interceptor {

            return Interceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                val cacheControl = originalResponse.header("Cache-Control")
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
                ) {
                    originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build()
                } else {
                    originalResponse
                }
            }
        }

        fun responseInterceptorOffline(context: Context): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                if (!Singleton.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build()
                }
                chain.proceed(request)

            }
        }


    }
}






