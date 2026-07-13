package com.pascal.xpense.di

import androidx.room.RoomDatabase
import com.pascal.xpense.data.local.database.AppDatabase
import com.pascal.xpense.data.local.database.getRoomDatabase
import com.pascal.xpense.data.local.repository.LocalRepository
import com.pascal.xpense.data.local.repository.LocalRepositoryImpl
import com.pascal.xpense.data.remote.api.KtorClientApi
import com.pascal.xpense.data.repository.RemoteRepository
import com.pascal.xpense.data.repository.RemoteRepositoryImpl
import com.pascal.xpense.domain.usecase.local.LocalUseCase
import com.pascal.xpense.domain.usecase.local.LocalUseCaseImpl
import com.pascal.xpense.domain.usecase.remote.RemoteUseCase
import com.pascal.xpense.domain.usecase.remote.RemoteUseCaseImpl
import com.pascal.xpense.getDatabaseBuilder
import com.pascal.xpense.ui.screen.detail.DetailViewModel
import com.pascal.xpense.ui.screen.favorite.FavoriteViewModel
import com.pascal.xpense.ui.screen.home.HomeViewModel
import com.pascal.xpense.ui.screen.profile.ProfileViewModel
import com.pascal.xpense.ui.screen.manga.MangaViewModel
import com.pascal.xpense.ui.screen.search.SearchViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Database
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
    single<AppDatabase> { getRoomDatabase(get()) }

    // Data source
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }

    // API client
    singleOf(::KtorClientApi)

    // Repository
    singleOf(::RemoteRepositoryImpl) { bind<RemoteRepository>() }

    // UseCases
    singleOf(::LocalUseCaseImpl) { bind<LocalUseCase>() }
    singleOf(::RemoteUseCaseImpl) { bind<RemoteUseCase>() }

    // ViewModels
    singleOf(::HomeViewModel)
    singleOf(::MangaViewModel)
    singleOf(::SearchViewModel)
    singleOf(::FavoriteViewModel)
    singleOf(::ProfileViewModel)
    singleOf(::DetailViewModel)
}