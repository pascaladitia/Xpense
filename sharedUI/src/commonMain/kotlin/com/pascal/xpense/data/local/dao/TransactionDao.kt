package com.pascal.xpense.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pascal.xpense.data.local.entity.CategoryTotal
import com.pascal.xpense.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TransactionEntity): Long

    @Delete
    suspend fun delete(entity: TransactionEntity): Int

    @Query("SELECT * FROM transactions ORDER BY date DESC, id DESC")
    fun observeAll(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY date DESC, id DESC")
    suspend fun getAll(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Long): TransactionEntity?

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions")
    fun observeTotalExpense(): Flow<Double>

    @Query("SELECT * FROM transactions WHERE date LIKE :yearMonth || '%' ORDER BY date DESC, id DESC")
    fun observeByMonth(yearMonth: String): Flow<List<TransactionEntity>>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE date LIKE :yearMonth || '%'")
    fun observeMonthlyExpense(yearMonth: String): Flow<Double>

    @Query("SELECT category, COALESCE(SUM(amount), 0) as total FROM transactions GROUP BY category ORDER BY total DESC")
    fun observeExpenseByCategory(): Flow<List<CategoryTotal>>

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()
}
