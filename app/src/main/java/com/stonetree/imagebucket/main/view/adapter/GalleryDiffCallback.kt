package com.stonetree.imagebucket.main.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.stonetree.imagebucket.main.model.GalleryModel

class GalleryDiffCallback : DiffUtil.ItemCallback<GalleryModel>() {

    override fun areItemsTheSame(
        oldItem: GalleryModel,
        newItem: GalleryModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: GalleryModel,
        newItem: GalleryModel
    ): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }
}
