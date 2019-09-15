package com.stonetree.imagebucket.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.stonetree.imagebucket.R
import com.stonetree.imagebucket.main.model.GalleryModel
import com.stonetree.imagebucket.main.view.IPicture

class GalleryAdapter(private val picture: IPicture) :
    ListAdapter<GalleryModel, MainViewHolder>(MainDiffCallback()) {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position).let { model ->
            with(holder) {
                itemView.tag = position
                onBind(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_main, parent, false
            ),
            picture
        )
    }
}
