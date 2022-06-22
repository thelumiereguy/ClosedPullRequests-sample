package dev.thelumiereguy.helpers.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtilCallback : DiffUtil.ItemCallback<BaseListItem>() {

    override fun areItemsTheSame(oldItem: BaseListItem, newItem: BaseListItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseListItem, newItem: BaseListItem): Boolean {
        return oldItem == newItem
    }
}
