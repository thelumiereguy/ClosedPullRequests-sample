package dev.thelumiereguy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.thelumiereguy.data.local.dao.ClosedPRDao
import dev.thelumiereguy.data.local.models.ClosedPREntity

@Database(entities = [ClosedPREntity::class], version = 1, exportSchema = false)
abstract class PullRequestsDatabase : RoomDatabase() {

    abstract fun closedPRDao(): ClosedPRDao
}