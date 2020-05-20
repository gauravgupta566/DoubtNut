package com.example.doubtnut.ui.main

import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doubtnut.R
import com.example.doubtnut.adapter.HeadlineAdapter
import com.example.doubtnut.responsemodel.Article
import com.example.doubtnut.responsemodel.HeadlineModel
import com.example.doubtnut.utils.CustomTabHelper
import com.example.doubtnut.utils.RecyclerItemClickListener
import com.example.doubtnut.utils.VerticalSpaceItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.headlinelist_fragment.*
import org.reactivestreams.Publisher
import retrofit2.HttpException
import retrofit2.Response


class HeadlineListFragment : Fragment() {


    private var customTabHelper: CustomTabHelper = CustomTabHelper()

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter:HeadlineAdapter
    private lateinit var  manager:LinearLayoutManager
    private  var resultSize=0
    private var pagination: PublishProcessor<Int>? = null
    private var compositeDisposable: CompositeDisposable? = null

    var list: ArrayList<Article> = java.util.ArrayList()
    var pageNumber=1
    var loading =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagination = PublishProcessor.create()
        compositeDisposable = CompositeDisposable()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.headlinelist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setUpLoadMoreListener()



    }


    private fun initRecyclerView() {
        val density = Resources.getSystem().getDisplayMetrics().density
        val setHeightRecyclerViewItem = Math.round(15 * density)

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

                        loadUrl(url)

                    }
                })
        )
    }

    private fun loadUrl(url: String) {

        val builder = CustomTabsIntent.Builder()

        // modify toolbar color
        builder.setToolbarColor(ContextCompat.getColor(context!!, R.color.colorAccent))

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

        val packageName = customTabHelper.getPackageNameToUse(context!!, url)

        customTabsIntent.intent.setPackage(packageName)
        customTabsIntent.launchUrl(context!!, Uri.parse(url))


    }

    private fun setUpLoadMoreListener() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount=adapter.itemCount
                val lastVisibleItem=manager.findLastVisibleItemPosition()

                if (!loading&&totalItemCount<=(lastVisibleItem+1)&& resultSize>totalItemCount){
                    pageNumber++
                    loading=true
                    pagination?.onNext(pageNumber)

                }


            }

        })
          }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //fetchData()

        val disposable = pagination?.onBackpressureDrop()
            ?.doOnNext { integer ->

                loading=true
            }
            ?.concatMap<Response<HeadlineModel>>(object :Function<Int, Publisher<Response<HeadlineModel>>> {
                override fun apply(t: Int): Publisher<Response<HeadlineModel>> {

                    return viewModel.getHeadlingList(t)


                }



            })
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnNext(object : Consumer<Response<HeadlineModel>> {
                @Throws(Exception::class)
                override fun accept(t: Response<HeadlineModel>?) {
                    val response=t?.body()
                    list.addAll(response!!.articles)
                    resultSize=response!!.totalResults
                    adapter.notifyDataSetChanged()

                    //    mAdapter.setUsers(gitHubUsers.body())
                    loading=false

                }

                /* requestOnWay = false
                 loadUser.setVisibility(View.INVISIBLE)
           */
            })
            ?.doOnError { throwable ->
                if (throwable is HttpException) {

                }

            }
            ?.subscribe()

        compositeDisposable?.add(disposable!!)

        pagination?.onNext(pageNumber)



    }



    override fun onDetach() {
        super.onDetach()
        compositeDisposable?.dispose()

    }


    }





