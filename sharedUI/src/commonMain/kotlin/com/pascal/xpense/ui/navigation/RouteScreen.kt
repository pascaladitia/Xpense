@file:OptIn(
    ExperimentalMotionApi::class,
    ExperimentalSharedTransitionApi::class
)

package com.pascal.xpense.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pascal.xpense.ui.screen.addtransaction.AddTransactionRoute
import com.pascal.xpense.ui.screen.analytics.AnalyticsRoute
import com.pascal.xpense.ui.screen.chat.ChatRoute
import com.pascal.xpense.ui.screen.dashboard.DashboardRoute
import com.pascal.xpense.ui.screen.profile.ProfileRoute
import com.pascal.xpense.ui.screen.splash.SplashRoute

@Composable
fun RouteScreen(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = listOf(
        Screen.DashboardScreen.route,
        Screen.AnalyticsScreen.route,
        Screen.ChatScreen.route,
        Screen.ProfileScreen.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        SharedTransitionLayout {
            val sharedScope: SharedTransitionScope = this

            NavHost(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ) {
                composable(route = Screen.SplashScreen.route) {
                    val animScope: AnimatedVisibilityScope = this

                    SplashRoute(
                        sharedTransitionScope = sharedScope,
                        animatedVisibilityScope = animScope,
                    ) {
                        navController.navigate(Screen.DashboardScreen.route) {
                            popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
                composable(route = Screen.DashboardScreen.route) {
                    val animScope: AnimatedVisibilityScope = this

                    DashboardRoute(
                        sharedTransitionScope = sharedScope,
                        animatedVisibilityScope = animScope,
                        onAddTransaction = {
                            navController.navigate(Screen.AddTransactionScreen.route)
                        }
                    )
                }
                composable(route = Screen.AddTransactionScreen.route) {
                    AddTransactionRoute(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(route = Screen.AnalyticsScreen.route) {
                    AnalyticsRoute()
                }
                composable(route = Screen.ChatScreen.route) {
                    ChatRoute()
                }
                composable(route = Screen.ProfileScreen.route) {
                    ProfileRoute(onBookMark = {})
                }
            }
        }
    }
}
