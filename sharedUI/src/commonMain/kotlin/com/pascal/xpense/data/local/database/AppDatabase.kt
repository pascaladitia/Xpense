package com.pascal.xpense.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.pascal.xpense.data.local.dao.TransactionDao
import com.pascal.xpense.data.local.entity.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@TypeConverters(Converters::class)
@ConstructedBy(AppDatabaseConstructor::class)
@Database(
    entities = [
        TransactionEntity::class
    ], version = 3
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun transactionDao(): TransactionDao
    override fun clearAllTables(): Unit {}
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

interface DB {
    fun clearAllTables(): Unit {}
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}