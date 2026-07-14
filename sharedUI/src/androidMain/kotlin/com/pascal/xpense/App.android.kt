package com.pascal.xpense

import androidx.room.Room
import androidx.room.RoomDatabase
import com.pascal.xpense.data.local.database.AppDatabase

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val appContext = ContextUtils.context
    val dbFile = appContext.getDatabasePath("app.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
