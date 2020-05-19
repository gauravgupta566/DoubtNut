package com.example.doubtnut.ui.main

import androidx.lifecycle.ViewModel
import com.example.doubtnut.responsemodel.HeadlineModel
import com.example.doubtnut.utils.ApiClient
import com.example.doubtnut.utils.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel




    fun fetchData():Observable<HeadlineModel>{

        val service=  ApiClient.getClient().create(ApiInterface::class.java)

        val observale=  service.getHeadlineList("us")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return observale

    }


}
