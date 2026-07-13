package com.pascal.xpense.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "transactions"
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val amount: Double,
    val category: String,

    val date: String,

    val type: String,

    val note: String? = null,
    val attachmentPath: String? = null,
    val createdAt: Long = 0
) {
    companion object {
        const val TYPE_EXPENSE = "EXPENSE"
    }
}

data class CategoryTotal(
    val category: String,
    val total: Double
)
