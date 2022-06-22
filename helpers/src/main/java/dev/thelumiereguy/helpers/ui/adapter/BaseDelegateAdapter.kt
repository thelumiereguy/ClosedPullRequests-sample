package dev.thelumiereguy.helpers.ui.adapter

import androidx.recyclerview.widget.AsyncDifferConfig
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class BaseDelegateAdapter(
    delegates: List<AdapterDelegate<List<BaseListItem>>>,
    differConfig: AsyncDifferConfig<BaseListItem> = BaseDiffUtilConfig.create()
) : AsyncListDifferDelegationAdapter<BaseListItem>(
    differConfig
) {
    init {
        delegates.forEach(delegatesManager::addDelegate)
    }
}
