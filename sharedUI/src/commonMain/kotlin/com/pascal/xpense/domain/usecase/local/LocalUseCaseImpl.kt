package com.pascal.xpense.domain.usecase.local

import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.data.local.repository.LocalRepository
import com.pascal.xpense.domain.model.CategoryBreakdown
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocalUseCaseImpl(
    private val repository: LocalRepository,
) : LocalUseCase {

    override suspend fun saveTransaction(entity: TransactionEntity): Long {
        return repository.addTransaction(entity)
    }

    override suspend fun deleteTransaction(entity: TransactionEntity) {
        repository.removeTransaction(entity)
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return repository.observeAllTransactions()
    }

    override fun observeTotalExpense(): Flow<Double> {
        return repository.observeTotalExpense()
    }

    override fun observeTransactionsByMonth(yearMonth: String): Flow<List<TransactionEntity>> {
        return repository.observeTransactionsByMonth(yearMonth)
    }

    override fun observeMonthlyExpense(yearMonth: String): Flow<Double> {
        return repository.observeMonthlyExpense(yearMonth)
    }

    override fun observeExpenseByCategory(): Flow<List<CategoryBreakdown>> {
        return repository.observeExpenseByCategory().map { list ->
            val total = list.sumOf { it.total }
            if (total <= 0) return@map emptyList()
            list.map { item ->
                CategoryBreakdown(
                    category = item.category,
                    total = item.total,
                    percentage = (item.total / total).toFloat()
                )
            }
        }
    }
}
