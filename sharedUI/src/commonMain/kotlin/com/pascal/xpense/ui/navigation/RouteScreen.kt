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
import com.pascal.xpense.data.preferences.PrefLogin
import com.pascal.xpense.ui.screen.detail.DetailRoute
import com.pascal.xpense.ui.screen.favorite.FavoriteRoute
import com.pascal.xpense.ui.screen.home.HomeRoute
import com.pascal.xpense.ui.screen.manga.MangaRoute
import com.pascal.xpense.ui.screen.onboarding.OnboardingRoute
import com.pascal.xpense.ui.screen.profile.ProfileRoute
import com.pascal.xpense.ui.screen.search.SearchRoute
import com.pascal.xpense.ui.screen.splash.SplashRoute
import com.pascal.xpense.utils.base.getFromPreviousBackStack
import com.pascal.xpense.utils.base.saveToCurrentBackStack

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RouteScreen(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.HomeScreen.route,
                    Screen.MangaScreen.route,
                    Screen.SearchScreen.route,
                    Screen.FavoriteScreen.route,
                    Screen.ProfileScreen.route
                )) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        SharedTransitionLayout {
            val sharedScope: SharedTransitionScope = this

            NavHost(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                navController = navController,
                startDestination = Screen.SplashScreen.route,
            ) {
                composable(route = Screen.SplashScreen.route) {
                    SplashRoute(
                        paddingValues = paddingValues
                    ) {
                        val route = if (PrefLogin.getIsOnboarding()) {
                            Screen.HomeScreen.route
                        } else {
                            Screen.OnboardingScreen.route
                        }

                        navController.navigate(route) {
                            popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
                composable(route = Screen.OnboardingScreen.route) {
                    OnboardingRoute(
                        onNext = {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.OnboardingScreen.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(route = Screen.HomeScreen.route) {
                    val animScope: AnimatedVisibilityScope = this
                    HomeRoute(
                        sharedTransitionScope = sharedScope,
                        animatedVisibilityScope = animScope,
                        onDetail = {
                            saveToCurrentBackStack(navController, "articles", it)
                            navController.navigate(Screen.DetailScreen.route)
                        }
                    )
                }
                composable(route = Screen.DetailScreen.route) {
                    val animScope: AnimatedVisibilityScope = this
                    DetailRoute(
                        sharedTransitionScope = sharedScope,
                        animatedVisibilityScope = animScope,
                        item = getFromPreviousBackStack(navController, "articles"),
                        onNavBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(route = Screen.MangaScreen.route) {
                    MangaRoute(
                        paddingValues = paddingValues,
                        onDetail = {}
                    )
                }
                composable(route = Screen.SearchScreen.route) {
                    SearchRoute(
                        paddingValues = paddingValues,
                        onDetail = {}
                    )
                }
                composable(route = Screen.FavoriteScreen.route) {
                    FavoriteRoute(
                        paddingValues = paddingValues,
                        onDetail = {}
                    )
                }
                composable(route = Screen.ProfileScreen.route) {
                    ProfileRoute(
                        onBookMark = {
                            navController.navigate(Screen.ProfileScreen.route)
                        },
                    )
                }
            }
        }
    }
}