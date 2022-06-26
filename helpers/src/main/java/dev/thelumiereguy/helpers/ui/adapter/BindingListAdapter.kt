package dev.thelumiereguy.helpers.ui.adapter

/*
 * Created by Piyush Pradeepkumar on 25/06/22, 10:37 AM
 */

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <reified T : BaseListItem, reified Binding : ViewBinding> adapter(
    noinline onCreate: (parent: ViewGroup) -> Binding,
    noinline onBind: Binding.(item: T) -> Unit
) = BindingListAdapter(onCreate, onBind)

class BindingListAdapter<out T : BaseListItem, Binding : ViewBinding>(
    private val onCreate: (parent: ViewGroup) -> Binding,
    private val onBind: Binding.(item: T) -> Unit,
) : ListAdapter<@UnsafeVariance T, BindingViewHolder<Binding>>(
    BaseDiffUtilConfig.create()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<Binding> {
        return BindingViewHolder(onCreate(parent))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<Binding>, position: Int) {
        holder.binding.onBind(getItem(position))
    }
}

class BindingViewHolder<T : ViewBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root)
