package dev.thelumiereguy.data.local

import dev.thelumiereguy.data.local.dao.ClosedPRDao
import dev.thelumiereguy.data.local.models.ClosedPREntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach

class FakeClosedPRDao : ClosedPRDao {

    private val localDataChannel = MutableStateFlow<List<ClosedPREntity>?>(null)

    override suspend fun insert(list: List<ClosedPREntity>) {
        localDataChannel.emit(localDataChannel.value?.plus(list) ?: list)
    }

    override suspend fun insert(closedPREntity: ClosedPREntity) {
    }

    override fun getClosedPRs(): Flow<List<ClosedPREntity>?> {
        return localDataChannel.onEach {
            println("DB $it")
        }
    }
}
