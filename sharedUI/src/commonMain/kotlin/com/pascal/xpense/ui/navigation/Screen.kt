package com.pascal.xpense.ui.navigation

sealed class Screen(val route: String) {
    data object DashboardScreen : Screen("dashboard")
    data object AnalyticsScreen : Screen("analytics")
    data object BudgetScreen : Screen("budget")
    data object ProfileScreen : Screen("profile")
    data object AddTransactionScreen : Screen("add_transaction")
}
