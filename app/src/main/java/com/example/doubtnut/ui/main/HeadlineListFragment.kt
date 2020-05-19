package com.example.doubtnut.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubtnut.adapter.HeadlineAdapter
import com.example.doubtnut.responsemodel.Article
import com.example.doubtnut.responsemodel.HeadlineModel
import com.example.doubtnut.utils.RecyclerItemClickListener
import com.example.doubtnut.utils.VerticalSpaceItemDecoration
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.headlinelist_fragment.*


class HeadlineListFragment : Fragment() {

    companion object {
        fun newInstance() = HeadlineListFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter:HeadlineAdapter
     var list: ArrayList<Article> = java.util.ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(com.example.doubtnut.R.layout.headlinelist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val density = Resources.getSystem().getDisplayMetrics().density

        val setHeightRecyclerViewItem = Math.round(15 * density)

        var manager=LinearLayoutManager(context)
        adapter= HeadlineAdapter(list)
        recyclerView.layoutManager=manager
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration( VerticalSpaceItemDecoration(setHeightRecyclerViewItem))

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context!!,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {


                    }
                })
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

      val gettingObservable=  viewModel.fetchData()

        gettingObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getHeadlineObserver())



        }


    private fun getHeadlineObserver(): Observer<HeadlineModel> {
        return object : Observer<HeadlineModel> {

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(headline: HeadlineModel) {
                list.addAll(headline.articles)
                adapter.notifyDataSetChanged()

            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {

            }
        }
    }



    }





