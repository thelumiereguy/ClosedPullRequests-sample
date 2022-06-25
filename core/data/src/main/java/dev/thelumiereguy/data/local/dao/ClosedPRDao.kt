package dev.thelumiereguy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.thelumiereguy.data.local.models.ClosedPREntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClosedPRDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<ClosedPREntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(closedPREntity: ClosedPREntity)

    @Query("SELECT * from closed_pr")
    fun getClosedPRs(): Flow<List<ClosedPREntity>?>
}
