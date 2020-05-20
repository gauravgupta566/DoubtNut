package com.example.doubtnut.ui.main

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.doubtnut.utils.CustomTabHelper
import com.example.doubtnut.utils.WebViewActivity
import kotlinx.android.synthetic.main.fragment_details_headline.view.*


class DetailsHeadline : Fragment() {

    private var customTabHelper: CustomTabHelper = CustomTabHelper()

    var url:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        url = arguments!!.getString("url")


        return inflater.inflate(com.example.doubtnut.R.layout.fragment_details_headline, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.progressBar.visibility=View.VISIBLE

        val builder = CustomTabsIntent.Builder()

        // modify toolbar color
        builder.setToolbarColor(ContextCompat.getColor(context!!, com.example.doubtnut.R.color.colorAccent))

        // add share button to overflow menu
        builder.addDefaultShareMenuItem()

        val anotherCustomTab = CustomTabsIntent.Builder().build()

        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.setData(Uri.parse(url))

        val pendingIntent = PendingIntent.getActivity(context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        // add menu item to oveflow
        builder.addMenuItem("Sample item", pendingIntent)


        // menu item icon
        // val bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
        // builder.setActionButton(bitmap, "Android", pendingIntent, true)

        // modify back button icon
        // builder.setCloseButtonIcon(bitmap)

        // show website title

        builder.setShowTitle(true)

        // animation for enter and exit of tab
        builder.setStartAnimations(context!!, android.R.anim.fade_in, android.R.anim.fade_out)
        builder.setExitAnimations(context!!, android.R.anim.fade_in, android.R.anim.fade_out)

        val customTabsIntent = builder.build()

        val packageName = customTabHelper.getPackageNameToUse(context!!, url!!)

        if (packageName == null) {
            // if chrome not available open in web view
            val intentOpenUri = Intent(context!!, WebViewActivity::class.java)
            intentOpenUri.putExtra(WebViewActivity.EXTRA_URL, Uri.parse(url).toString())
            startActivity(intentOpenUri)
        } else {
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(context!!, Uri.parse(url))

        }

        view.progressBar.visibility=View.GONE

    }

    override fun onDetach() {
        super.onDetach()
    }


    }



