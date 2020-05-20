package com.example.doubtnut

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.doubtnut.utils.CustomFontFamily

class MyApplication :Application(){



    companion object{
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null

        lateinit var customFontFamily: CustomFontFamily

    }


    override fun onCreate() {
        super.onCreate()
        context = this

        customFontFamily = CustomFontFamily.customFontFamily
        // add your custom fonts here with your own custom name.


        customFontFamily.addFont("robotobold", "Roboto-Bold.ttf")
        customFontFamily.addFont("robotolight", "Roboto-Light.ttf")
        customFontFamily.addFont("robotomedium", "Roboto-Medium.ttf")
        customFontFamily.addFont("robotoregular", "Roboto-Regular.ttf")
        customFontFamily.addFont("robotothin", "Roboto-Thin.ttf")


    }




}