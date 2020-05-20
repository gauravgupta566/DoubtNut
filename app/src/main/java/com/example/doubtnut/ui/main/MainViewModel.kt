package com.example.doubtnut.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.doubtnut.responsemodel.HeadlineModel
import com.example.doubtnut.utils.ApiClient
import com.example.doubtnut.utils.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(application:Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext



    fun fetchData(pageNumber:Int):Observable<HeadlineModel>{

        val service=  ApiClient.getClient(context).create(ApiInterface::class.java)

        val observale=  service.getHeadlineList("us",pageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return observale



    }





}
