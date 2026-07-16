package com.pascal.xpense.di

import androidx.room.RoomDatabase
import com.pascal.xpense.data.local.database.AppDatabase
import com.pascal.xpense.data.local.database.getRoomDatabase
import com.pascal.xpense.data.repository.local.LocalRepository
import com.pascal.xpense.data.repository.local.LocalRepositoryImpl
import com.pascal.xpense.data.remote.api.AIClientApi
import com.pascal.xpense.data.remote.client.client
import com.pascal.xpense.data.repository.remote.RemoteRepository
import com.pascal.xpense.data.repository.remote.RemoteRepositoryImpl
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.domain.usecase.local.LocalUseCaseImpl
import com.pascal.xpense.domain.usecase.remote.RemoteUseCase
import com.pascal.xpense.domain.usecase.remote.RemoteUseCaseImpl
import com.pascal.xpense.getDatabaseBuilder
import com.pascal.xpense.ui.screen.addtransaction.AddTransactionViewModel
import com.pascal.xpense.ui.screen.analytics.AnalyticsViewModel
import com.pascal.xpense.ui.screen.chat.ChatViewModel
import com.pascal.xpense.ui.screen.dashboard.DashboardViewModel
import com.pascal.xpense.ui.screen.profile.ProfileViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Network
    single { client }
    singleOf(::AIClientApi)

    // Database
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
    single<AppDatabase> { getRoomDatabase(get()) }

    // Data source
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    singleOf(::RemoteRepositoryImpl) { bind<RemoteRepository>() }

    // UseCases
    singleOf(::LocalUseCaseImpl) { bind<LocalUseCase>() }
    singleOf(::RemoteUseCaseImpl) { bind<RemoteUseCase>() }

    // ViewModels
    singleOf(::DashboardViewModel)
    singleOf(::AddTransactionViewModel)
    singleOf(::AnalyticsViewModel)
    singleOf(::ChatViewModel)
    singleOf(::ProfileViewModel)
}
