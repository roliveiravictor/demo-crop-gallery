package com.stonetree.imagebucket.core.extensions

import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import androidx.databinding.BindingAdapter
import com.stonetree.imagebucket.core.network.NetworkState
import com.stonetree.imagebucket.core.network.NetworkState.Companion.LOADING
import com.stonetree.imagebucket.core.network.Status

@BindingAdapter("isGone")
fun bindIsGone(view: View, network: NetworkState?) {
    when (network) {
        LOADING -> view.visibility = VISIBLE
        else -> view.visibility = GONE
    }
}

@BindingAdapter("isIdle")
fun bindIsIdle(view: View, network: NetworkState?) {
    when (network?.status) {
        Status.RUNNING -> view.visibility = GONE
        else -> view.visibility = VISIBLE
    }
}