package com.example.doubtnut.ui.main

import android.content.res.Resources
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Resources::class)
class HeadlineListFragmentTest {

    lateinit var fragment: HeadlineListFragment

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fragment = PowerMockito.spy(HeadlineListFragment())
        PowerMockito.mockStatic(Resources::class.java)

    }

    @Test
    fun initRecyclerView(){

             /*
      Resources.getSystem().getDisplayMetrics().density
      doNothing().`when`(fragment).initRecyclerView()
        doNothing().`when`(fragment).setUpLoadMoreListener()
      */
        fragment.initRecyclerView()


        /*
        *  initRecyclerView()
        setUpLoadMoreListener()

        * */
    }




}