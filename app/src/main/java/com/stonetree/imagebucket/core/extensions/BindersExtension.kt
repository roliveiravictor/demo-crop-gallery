package com.stonetree.imagebucket.core.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import com.stonetree.imagebucket.core.network.NetworkState
import com.stonetree.imagebucket.core.network.Status

@BindingAdapter("isGone")
fun bindIsGone(view: View, network: NetworkState?) {
    when (network) {
        NetworkState.LOADING -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("isIdle")
fun bindIsIdle(view: View, network: NetworkState?) {
    when (network?.status) {
        Status.SUCCESS -> view.visibility = View.VISIBLE
        Status.FAILED -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}