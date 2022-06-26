package dev.thelumiereguy.helpers.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import java.util.concurrent.Executors

object BaseDiffUtilConfig {
    fun <T : BaseListItem> create(): AsyncDifferConfig<T> {
        return CustomThreadDiffUtilConfig.create(BaseDiffUtilCallback())
    }
}

private object CustomThreadDiffUtilConfig {
    fun <T> create(diffCallback: DiffUtil.ItemCallback<T>): AsyncDifferConfig<T> {
        val bgPool = Executors.newFixedThreadPool(2)
        return AsyncDifferConfig.Builder(diffCallback)
            .setBackgroundThreadExecutor(bgPool)
            .build()
    }
}

class BaseDiffUtilCallback<T : BaseListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
