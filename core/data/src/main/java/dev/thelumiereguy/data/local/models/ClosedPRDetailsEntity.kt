package dev.thelumiereguy.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "closed_pr_details")
data class ClosedPRDetailsEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "pr_id")
    val pr_id: Long,

    @ColumnInfo(name = "comments")
    val comments: Int,

    @ColumnInfo(name = "commits")
    val commits: Int,

    @ColumnInfo(name = "changed_files")
    val changedFiles: Int,
)