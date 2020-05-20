package com.example.doubtnut.utils

import android.graphics.Typeface
import android.util.Log
import com.example.doubtnut.MyApplication
import java.util.HashMap

class CustomFontFamily {

    internal var fontMap = HashMap<String, String>()

    companion object{

        var customFontFamily: CustomFontFamily=CustomFontFamily()


    }

    fun addFont(alias: String, fontName: String) {
        fontMap[alias] = fontName
    }

    fun getFont(alias: String): Typeface? {
        val fontFilename = fontMap[alias]
        if (fontFilename == null) {
            Log.e("", "Font not available with name $alias")
            return null
        } else {
            return Typeface.createFromAsset(
                MyApplication.context?.getAssets(),
                "font/$fontFilename"
            )
        }
    }

}