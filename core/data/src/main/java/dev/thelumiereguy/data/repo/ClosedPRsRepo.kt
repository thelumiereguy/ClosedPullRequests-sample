package dev.thelumiereguy.data.repo

import dev.thelumiereguy.data.repo.models.ClosedPR
import dev.thelumiereguy.helpers.framework.ResultState
import kotlinx.coroutines.flow.Flow

interface ClosedPRsRepo {
    fun getAllClosedPRs(): Flow<ResultState<List<ClosedPR>>>
    fun loadNextPage()
}

// first try fetch
// failed -> send internet error
// success -> send list of data
// paginate -> add more list of data
// has data, but failed api -> send existing data with retry option and error
// api call in progress - loading
// has data with api call in progress, - loading with data
