package com.pascal.xpense.di

import androidx.room.RoomDatabase
import com.pascal.xpense.data.local.database.AppDatabase
import com.pascal.xpense.data.local.database.getRoomDatabase
import com.pascal.xpense.data.local.repository.LocalRepository
import com.pascal.xpense.data.local.repository.LocalRepositoryImpl
import com.pascal.xpense.data.remote.api.TransactionAI
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.domain.usecase.local.LocalUseCaseImpl
import com.pascal.xpense.getDatabaseBuilder
import com.pascal.xpense.ui.screen.addtransaction.AddTransactionViewModel
import com.pascal.xpense.ui.screen.analytics.AnalyticsViewModel
import com.pascal.xpense.ui.screen.budget.BudgetViewModel
import com.pascal.xpense.ui.screen.dashboard.DashboardViewModel
import com.pascal.xpense.ui.screen.profile.ProfileViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Database
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
    single<AppDatabase> { getRoomDatabase(get()) }

    // Data source
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }

    // AI
    singleOf(::TransactionAI)

    // UseCases
    singleOf(::LocalUseCaseImpl) { bind<LocalUseCase>() }

    // ViewModels
    singleOf(::DashboardViewModel)
    singleOf(::AddTransactionViewModel)
    singleOf(::AnalyticsViewModel)
    singleOf(::BudgetViewModel)
    singleOf(::ProfileViewModel)
}
