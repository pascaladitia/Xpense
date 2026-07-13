package com.pascal.xpense.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash")
    data object OnboardingScreen: Screen("onboarding")
    data object HomeScreen: Screen("home")
    data object MangaScreen: Screen("manga")
    data object SearchScreen: Screen("search")
    data object FavoriteScreen: Screen("favorite")
    data object ProfileScreen: Screen("profile")
    data object DetailScreen: Screen("detail")
}