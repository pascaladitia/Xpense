package com.pascal.xpense.data.local.repository

import com.pascal.xpense.data.local.entity.CategoryTotal
import com.pascal.xpense.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun addTransaction(transaction: TransactionEntity): Long

    suspend fun removeTransaction(transaction: TransactionEntity)

    fun observeAllTransactions(): Flow<List<TransactionEntity>>

    suspend fun getTransactionById(id: Long): TransactionEntity?

    fun observeTotalIncome(): Flow<Double>

    fun observeTotalExpense(): Flow<Double>

    fun observeTransactionsByMonth(yearMonth: String): Flow<List<TransactionEntity>>

    fun observeMonthlyExpense(yearMonth: String): Flow<Double>

    fun observeExpenseByCategory(): Flow<List<CategoryTotal>>
}
