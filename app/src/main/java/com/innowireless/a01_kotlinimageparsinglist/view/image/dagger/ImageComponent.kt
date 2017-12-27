package com.innowireless.a01_kotlinimageparsinglist.view.image.dagger

import android.support.v4.app.Fragment
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImagePresenter
import dagger.Component

/**
 * Created by Owner on 2017-12-26.
 */
@ImageScope
@Component(modules = [(ImageModule::class)])
interface ImageComponent {
    fun inject(fragment: Fragment)

    fun imagePresenter(): ImagePresenter
}