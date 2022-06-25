package dev.thelumiereguy.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "closed_pr")
data class ClosedPREntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pr_id")
    val prId: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "closed_at")
    val closedAt: Long,

    @ColumnInfo(name = "user_login")
    val userLogin: String,

    @ColumnInfo(name = "branch_head")
    val branchHead: String,

    @ColumnInfo(name = "branch_base")
    val branchBase: String,
)