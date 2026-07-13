package com.pascal.xpense.domain.usecase.local

import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.domain.model.CategoryBreakdown
import kotlinx.coroutines.flow.Flow

interface LocalUseCase {

    suspend fun saveTransaction(entity: TransactionEntity): Long

    suspend fun deleteTransaction(entity: TransactionEntity)

    fun getAllTransactions(): Flow<List<TransactionEntity>>

    fun observeTotalIncome(): Flow<Double>

    fun observeTotalExpense(): Flow<Double>

    fun observeTransactionsByMonth(yearMonth: String): Flow<List<TransactionEntity>>

    fun observeMonthlyExpense(yearMonth: String): Flow<Double>

    fun observeExpenseByCategory(): Flow<List<CategoryBreakdown>>
}
