package com.example.doubtnut.utils


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.doubtnut.R

class WebViewActivity : AppCompatActivity() {

    companion object {
        val EXTRA_URL = "extra.url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
    }
}