package com.example.doubtnut.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doubtnut.R
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


    private lateinit var viewModel: MainViewModel
    private lateinit var adapter:HeadlineAdapter
    private lateinit var  manager:LinearLayoutManager
    private  var resultSize=0

    var list: ArrayList<Article> = java.util.ArrayList()
    var pageNumber=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.headlinelist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val density = Resources.getSystem().getDisplayMetrics().density
        val setHeightRecyclerViewItem = Math.round(15 * density)
        val navController = Navigation.findNavController(view)

        manager=LinearLayoutManager(context)
        adapter= HeadlineAdapter(list)
        recyclerView.layoutManager=manager
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration( VerticalSpaceItemDecoration(setHeightRecyclerViewItem))

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context!!,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        list[position].url
                        val url = list[position].url
                        val bundle = bundleOf(
                            "url" to url

                        )
                        navController.navigate(
                            R.id.action_headlinefragment_to_detailsfragment,

                            bundle
                        )


                    }
                })
        )


        setUpLoadMoreListener()

    }

    private fun setUpLoadMoreListener() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount=adapter.itemCount
                val lastVisibleItem=manager.findLastVisibleItemPosition()

                if (totalItemCount<=(lastVisibleItem+1)&& resultSize>totalItemCount){
                    pageNumber++
                    fetchData()


                }


            }

        })
          }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        fetchData()



        }

    private fun fetchData() {
        val gettingObservable=  viewModel.fetchData(pageNumber)

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
                if (pageNumber==1){
                    resultSize= headline.totalResults

                }

            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {

            }
        }
    }



    }





