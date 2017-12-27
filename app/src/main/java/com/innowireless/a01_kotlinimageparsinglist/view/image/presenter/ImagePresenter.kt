package com.innowireless.a01_kotlinimageparsinglist.view.image.presenter

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.innowireless.a01_kotlinimageparsinglist.data.GetImageInfoTask
import com.innowireless.a01_kotlinimageparsinglist.data.RandomUserApi
import com.innowireless.a01_kotlinimageparsinglist.data.RetrofitManager
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Owner on 2017-07-27.
 */
class ImagePresenter(override val view: ImageContract.View) : ImageContract.Presenter{
    private val TAG = "ImagePresenter"

    var imageItemList: MutableList<ImageItem> = ArrayList()

    // The minimum amount of items to have below your current scroll position before loading more
    var mVisibleThreshold: Int = 5
    // The current offset index of data you have loaded
    var mCurrentPage: Int = 0
    // The total number of items in the dataset after the last load
    var mPreviousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load
    var isLoading: Boolean = true
    // Sets the starting page index
    var mStartingPageIndex: Int = 0

    var mLastVisibleItem: Int = 0
    var mTotalItemCount: Int = 0

    override fun checkPassword(password: String): Boolean = password == "1111"

    override fun loadImagesWithRxJavaRetrofit() {
        val retrofit = RetrofitManager.retrofit.create(RandomUserApi::class.java)
        retrofit.getRandomUserInfo(20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.fromIterable(it.results) }        // Make new ovservable
                .subscribe({
                    println("next = $it")
                    view.addItem(it)
                }, {
                    println("Error message = ${it.message}")
                }, {
                    println("Completed")
                    view.notifyDataSetChanged()
                })
    }


    override fun loadImagesWithRxJava() {
        Observable.create<String> {
            try {
                val response = StringBuilder()

                (URL("https://randomuser.me/api/?results=20").openConnection() as HttpURLConnection).let {
                    it.connect()

                    it.inputStream.bufferedReader().use {
                        response.append(it.readLine())
                    }
                }

                it.onNext(response.toString())
                it.onComplete()
            }catch (e: Exception) {
                e.printStackTrace()
                it.onError(e)
            }
        }.distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println(it)
                    GetImageInfoTask().parseImage(it).forEach {
                        view.addItems(it)
                        view.notifyDataSetChanged()
                    }
                }, {
                    println("Load Images Error : ${it.message}")
                }, {
                    println("Completed")
                })
    }

    override fun loadImagesWithAsyncTask() {
        val imageData = GetImageInfoTask()

        imageData.imageInfoTaskCallback = object : GetImageInfoTask.ImageInfoTaskCallback {
            override fun onGetItem(item: ImageItem) {
                view.addItems(item)
                view.notifyDataSetChanged()
            }

            override fun onGetItemList(itemList: List<ImageItem>) {
                itemList.forEach {
                    view.addItems(it)
                    view.notifyDataSetChanged()
                }
            }
        }

        imageData.execute("https://randomuser.me/api/?results=20")
    }

    override fun showImages() {
        Log.d(TAG, "showImages")
        imageItemList.forEach {
            Log.d(TAG, "showImages, it = $it")
            view.addItems(it)
        }

        view.notifyDataSetChanged()
    }

    /*
        Reference site : http://gogorchg.tistory.com/entry/Android-RecyclerView-last-position-listener
        https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView
    */
    val rvScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = view.mLayoutManager
            mVisibleThreshold.times(layoutManager.spanCount)        // mVisibleThreshold = mVisibleThreshold * GridLayoutManager.spanCount = 10

            mTotalItemCount = layoutManager.itemCount
            mLastVisibleItem = layoutManager.findLastVisibleItemPosition()

//            Log.d(TAG, "onScrolled, layoutManager.spanCount = ${layoutManager.spanCount}, " +
//                    "mVisibleThreshold = ${mVisibleThreshold}, " +
//                    "mTotalItemCount = $mTotalItemCount, " +
//                    "mLastvisibleItem = $mLastVisibleItem, " +
//                    "mPreviousTotalItemCount = $mPreviousTotalItemCount")

            // If the total item count is zero and the previous isn't,
            // assume the list is invalidated and should be reset back to initial state
            if (mTotalItemCount < mPreviousTotalItemCount) {
                Log.d(TAG, "onScrolled, mTotalItemCount < mPreviousTotalItemCount")
                mCurrentPage = mStartingPageIndex
                mPreviousTotalItemCount = mTotalItemCount
                if (mTotalItemCount == 0)
                    isLoading = true
            }

            // If it's still loading, we check to see if the dataset count has changed,
            // if so, we conclude it has finished loading and update the current page number and total item count
            if (isLoading && mTotalItemCount > mPreviousTotalItemCount) {
                Log.d(TAG, "onScrolled, mTotalItemCount > mPreviousTotalItemCount")
                isLoading = false
                mPreviousTotalItemCount = mTotalItemCount
            }

            // If it isn't currently loading, we check to see if we have breached the visibleThreshold
            // and need to reload moredata
            // If we do need to reload some more data, we execute onLoadMore to fetch the data
            // threshold should reflect how many total columns there are too
            if (!isLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                Log.d(TAG, "onScrolled, mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)")
                mCurrentPage++
                loadImagesWithRxJavaRetrofit()
                isLoading = true
            }

        }
    }

}


