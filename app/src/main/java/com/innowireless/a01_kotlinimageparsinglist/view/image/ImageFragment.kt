package com.innowireless.a01_kotlinimageparsinglist.view.image

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innowireless.a01_kotlinimageparsinglist.R
import com.innowireless.a01_kotlinimageparsinglist.adapter.ImageListAdapter
import com.innowireless.a01_kotlinimageparsinglist.data.model.ImageItem
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImageContract
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImagePresenter
import kotlinx.android.synthetic.main.fragment_image.*


class ImageFragment : Fragment(), ImageContract.View {

    companion object {
        fun newInstance(): ImageFragment = ImageFragment()
    }

    private var imageAdapter: ImageListAdapter? = null

    private var presenter: ImagePresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater?.inflate(R.layout.fragment_image, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ImagePresenter(this)

        imageAdapter = ImageListAdapter(context)
        rv_image_info.adapter = imageAdapter

        presenter!!.loadImages()

    }

    override fun addItems(imageItem: ImageItem) {
        imageAdapter?.addItem(imageItem)
    }

    override fun notifyDataSetChanged() {
        activity.runOnUiThread(Runnable {
            imageAdapter?.notifyDataSetChanged()
        })
    }
}
