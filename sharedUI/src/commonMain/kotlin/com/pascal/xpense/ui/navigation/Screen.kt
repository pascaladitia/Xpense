package com.pascal.xpense.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash")
    data object DashboardScreen : Screen("dashboard")
    data object AnalyticsScreen : Screen("analytics")
    data object ChatScreen : Screen("chat")
    data object ProfileScreen : Screen("profile")
    data object AddTransactionScreen : Screen("add_transaction")
}
