package com.pascal.xpense.data.local.repository

import com.pascal.xpense.data.local.database.AppDatabase
import com.pascal.xpense.data.local.entity.CategoryTotal
import com.pascal.xpense.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val database: AppDatabase,
) : LocalRepository {

    private val dao get() = database.transactionDao()

    override suspend fun addTransaction(transaction: TransactionEntity): Long {
        return dao.insert(transaction)
    }

    override suspend fun removeTransaction(transaction: TransactionEntity) {
        dao.delete(transaction)
    }

    override fun observeAllTransactions(): Flow<List<TransactionEntity>> {
        return dao.observeAll()
    }

    override suspend fun getTransactionById(id: Long): TransactionEntity? {
        return dao.getById(id)
    }

    override fun observeTotalExpense(): Flow<Double> {
        return dao.observeTotalExpense()
    }

    override fun observeTransactionsByMonth(yearMonth: String): Flow<List<TransactionEntity>> {
        return dao.observeByMonth(yearMonth)
    }

    override fun observeMonthlyExpense(yearMonth: String): Flow<Double> {
        return dao.observeMonthlyExpense(yearMonth)
    }

    override fun observeExpenseByCategory(): Flow<List<CategoryTotal>> {
        return dao.observeExpenseByCategory()
    }
}
