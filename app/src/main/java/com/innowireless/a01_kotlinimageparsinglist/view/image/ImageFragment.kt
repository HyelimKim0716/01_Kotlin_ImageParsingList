package com.innowireless.a01_kotlinimageparsinglist.view.image

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innowireless.a01_kotlinimageparsinglist.R
import com.innowireless.a01_kotlinimageparsinglist.adapter.ImageListAdapter
import com.innowireless.a01_kotlinimageparsinglist.adapter.ImageResultListAdapter
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import com.innowireless.a01_kotlinimageparsinglist.data.model.Result
import com.innowireless.a01_kotlinimageparsinglist.view.image.dagger.DaggerImageComponent
import com.innowireless.a01_kotlinimageparsinglist.view.image.dagger.ImageModule
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImageContract
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImagePresenter
import kotlinx.android.synthetic.main.fragment_image.*
import javax.inject.Inject


class ImageFragment : Fragment(), ImageContract.View {

    companion object {
        fun newInstance(): ImageFragment = ImageFragment()
    }

    override val mLayoutManager: GridLayoutManager = GridLayoutManager(context, 2)

    private val mImageAdapter: ImageListAdapter by lazy {
        ImageListAdapter(context)
    }
    private val mImageResultAdapter: ImageResultListAdapter by lazy {
        ImageResultListAdapter(context)
    }

    private val mPresenter: ImagePresenter by lazy {
        ImagePresenter(this)
    }

//    @Inject
//    lateinit var mPresenter: ImagePresenter
//
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater?.inflate(R.layout.fragment_image, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mPresenter = DaggerImageComponent.builder().build().imagePresenter()

        btn_load_list.visibility = View.VISIBLE
        pb_loading.visibility = View.GONE

        rv_image_info?.run {
//            adapter = mImageAdapter
            adapter = mImageResultAdapter
            layoutManager = mLayoutManager
            addOnScrollListener(mPresenter.rvScrollListener)
        }

        btn_load_list.setOnClickListener {

            when (et_password.text.toString()) {
                "1111" -> {
                    btn_load_list.visibility = View.GONE
                    et_password.visibility = View.GONE
                    pb_loading.visibility = View.VISIBLE
                    mPresenter.loadImagesWithRxJava()
                }
                else -> {
                    btn_load_list.visibility = View.VISIBLE
                    et_password.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                }
            }
        }

        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0, mLayoutManager.itemCount - 1 -> mLayoutManager.spanCount
                else -> 1
            }
        }

//        mPresenter.loadImagesWithRxJavaRetrofit()
    }

    override fun addItem(result: Result) {
        mImageResultAdapter.addItem(result)
    }

    override fun addItems(imageItem: ImageItem) {
        mImageAdapter.addItem(imageItem)
    }

    override fun notifyDataSetChanged() {
        activity.runOnUiThread({
            mPresenter.isLoading = false
//            mImageAdapter.notifyDataSetChanged()
            mImageResultAdapter.notifyDataSetChanged()
            pb_loading.visibility = View.GONE
        })
    }
}
