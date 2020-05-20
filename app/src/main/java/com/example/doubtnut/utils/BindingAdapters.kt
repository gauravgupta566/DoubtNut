package com.example.doubtnut.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class BindingAdapters {

    companion object {


        @JvmStatic  @BindingAdapter("fonttv")
        fun setFont(textView: TextView, fontName: String) {
            textView.setTypeface(CustomFontFamily.customFontFamily.getFont(fontName))
        }

        @JvmStatic  @BindingAdapter("android:discovertakeimageurl")
        fun setDiscoverImageViewUrl(imageView: ImageView, url: String) {


            Glide.with(imageView.context)
                .load(url)
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                .into(imageView)

        }

    }
}