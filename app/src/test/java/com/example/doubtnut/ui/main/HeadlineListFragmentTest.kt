package com.example.doubtnut.ui.main

import android.app.Activity
import com.example.doubtnut.MainActivity
import org.junit.Before

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
class HeadlineListFragmentTest {

    lateinit var fragment: HeadlineListFragment

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fragment = PowerMockito.spy(HeadlineListFragment())

    }


}