package dev.thelumiereguy.helpers.ui.adapter

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import java.util.concurrent.Executors

object BaseDiffUtilConfig {
    fun create(): AsyncDifferConfig<BaseListItem> {
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
