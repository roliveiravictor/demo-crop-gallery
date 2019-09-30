package com.stonetree.imagebucket.gallery.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.stonetree.imagebucket.gallery.model.GalleryModel

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
