package com.innowireless.a01_kotlinimageparsinglist.view.image.dagger

import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImageContract
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImagePresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Owner on 2017-12-26.
 */
@Module
class ImageModule(val view: ImageContract.View){

//    @ImageScope
//    @Provides
//    fun providePresenter(presenter: ImageContract.Presenter): ImagePresenter {
//        Log.d("ImageModule", "providePresenter")
//        return presenter as ImagePresenter
//    }

    @ImageScope
    @Provides
    fun providePresenter(view: ImageContract.View): ImagePresenter {
        Log.d("ImageModule", "providePresenter")
        return ImagePresenter(view)
    }

    @ImageScope
    @Provides
    fun provideView(): ImageContract.View = view

}