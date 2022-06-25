/*
 * Created by Piyush Pradeepkumar on 25/06/22, 10:42 AM
 */

package dev.thelumiereguy.feature_closed_pr_list.adapter

import android.view.LayoutInflater
import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.feature_closed_pr_list.databinding.ItemClosedPullRequestBinding
import dev.thelumiereguy.helpers.ui.adapter.adapter

fun closedPRAdapter() = adapter(
    onCreate = { parent ->
        val layoutInflater = LayoutInflater.from(parent.context)
        ItemClosedPullRequestBinding.inflate(layoutInflater, parent, false)
    },
    onBind = { item: ClosedPR ->
        tvBookTitle.text = item.title
    }
)