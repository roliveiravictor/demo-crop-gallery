package com.stonetree.imagebucket.gallery.view.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.stonetree.imagebucket.core.viewholder.BaseViewHolder
import com.stonetree.imagebucket.databinding.ListItemMainBinding
import com.stonetree.imagebucket.gallery.model.GalleryModel
import com.stonetree.imagebucket.gallery.view.Picture

class GalleryViewHolder(
    private val bind: ListItemMainBinding,
    private val picture: Picture
) : BaseViewHolder<GalleryModel>(bind) {

    override fun onBind(data: GalleryModel) {
        bind.listener = View.OnLongClickListener {
            picture.delete(data.imageName)
            true
        }

        Glide.with(bind.root.context)
            .load(data.imageUrl)
            .into(bind.picture)
    }
}
