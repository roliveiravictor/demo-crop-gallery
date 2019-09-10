package com.stonetree.imagebucket.main.view.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.stonetree.imagebucket.core.viewholder.BaseViewHolder
import com.stonetree.imagebucket.databinding.ListItemMainBinding
import com.stonetree.imagebucket.main.model.MainModel
import com.stonetree.imagebucket.main.view.IPicture

class MainViewHolder(
    private val bind: ListItemMainBinding,
    private val picture: IPicture
) : BaseViewHolder<MainModel>(bind) {

    override fun onBind(model: MainModel) {
        bind.listener = View.OnLongClickListener {
            picture.delete(model.imageName)
            true
        }

        Glide.with(bind.root.context)
            .load(model.imageUrl)
            .into(bind.picture)
    }
}
