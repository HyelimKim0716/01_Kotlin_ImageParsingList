package com.innowireless.a01_kotlinimageparsinglist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.innowireless.a01_kotlinimageparsinglist.view.MainActivity
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImageContract
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImagePresenter
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


/**
 * Created by Owner on 2017-12-11.
 *
 * <Espresso Test Size>
 *  The problem with naming test types is that the names tend to rely on a shared understanding of what a particular phrase means.
 *
 *      Feature	                Small	    Medium	        Large
 *      Network access	        No	        localhost only	Yes
 *      Database	            No	        Yes	            Yes
 *      File system access	    No	        Yes	            Yes
 *      Use external systems	No	        Discouraged	    Yes
 *      Multiple threads	    No	        Yes	            Yes
 *      Sleep statements	    No	        Yes	            Yes
 *      System properties	    No	        Yes	            Yes
 *      Time limit (seconds)	60	        300	            900+
 *
 * this doesn't cover every possible type of test that might be run, but it certainly covers most of the major types that a project will run.
 * A Small test equates neatly to a unit test,
 * a Large test to an end-to-end or system test
 * and a Medium test to tests that ensure that two tiers in an application can communicate properly (often called an integration test).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    val mActivity: MainActivity by lazy {
        mRule.activity
    }

    lateinit var mView: ImageContract.View
    lateinit var mPresenter: ImagePresenter

    @Before
    fun setUp() {
        mView = Mockito.mock(ImageContract.View::class.java)
        mPresenter = ImagePresenter(mView)
    }

    @Test
    fun loadList() {

        onView(withText("Load")).check(matches(isDisplayed()))

        onView(withId(R.id.et_password)).perform(typeText("1111"))

//        onView(allOf(withId(R.id.btn_load_list)), withtext)
        onView(withId(R.id.btn_load_list))
                .perform(/*typeText("Hello!!"), */click())


        onView(withId(R.id.rv_image_info)).check(matches(isDisplayed()))


    }

    @Test
    fun testPassword() {
        mPresenter.checkPassword("1111")
//        Mockito.verify(onView(withId(R.id.pb_loading)).check(matches(isDisplayed())))
    }

    fun testSignInWithBlankPassword() {
//        Mockito.`when`(mPresenter.checkPassword("1111"))
//                .thenReturn(onView(withId(R.id.pb_loading)).check(matches(isDisplayed())))
    }

}