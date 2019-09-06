package com.stonetree.imagebucket.main.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.stonetree.imagebucket.main.model.MainModel

class MainDiffCallback : DiffUtil.ItemCallback<MainModel>() {

    override fun areItemsTheSame(
        oldItem: MainModel,
        newItem: MainModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MainModel,
        newItem: MainModel
    ): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }
}