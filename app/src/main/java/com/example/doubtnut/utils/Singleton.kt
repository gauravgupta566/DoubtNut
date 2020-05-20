package com.example.doubtnut.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager


class Singleton {

    companion object{

        fun dpToPx (value:Int):Int{
            val density = Resources.getSystem().getDisplayMetrics().density

            return Math.round(value * density)

        }


         fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }


}