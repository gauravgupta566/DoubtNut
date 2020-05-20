package com.example.doubtnut

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ReportFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.mock
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.lang.RuntimeException

@RunWith(PowerMockRunner::class)
@PrepareForTest(ReportFragment::class,Activity::class)
class MainActivityTest {

   lateinit var mainActivity:MainActivity
    lateinit var activity: Activity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivity = PowerMockito.spy<MainActivity>(MainActivity())
        activity = PowerMockito.spy<Activity>(Activity())


    }

    @Test
    fun onCreate() {


        mockStatic(ReportFragment::class.java)
        PowerMockito.doReturn(mock<AppCompatDelegate>(AppCompatDelegate::class.java))
            .`when`(mainActivity).getDelegate()

        PowerMockito.doReturn(mock(Application::class.java)).`when`(activity).getApplication()


        mainActivity.onCreate(mock(Bundle::class.java))

        verify(mainActivity, times(1)).setContentView(R.layout.main_activity)



        /*
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HeadlineListFragment())
                .commitNow()
        }
    }
*/


    }
}