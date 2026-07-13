package com.pascal.xpense.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val navigationItems = listOf(
                NavigationItem(
                    title = "Home",
                    iconFilled = Icons.Filled.Home,
                    iconOutlined = Icons.Outlined.Home,
                    screen = Screen.DashboardScreen
                ),
                NavigationItem(
                    title = "Analytics",
                    iconFilled = Icons.Filled.BarChart,
                    iconOutlined = Icons.Outlined.BarChart,
                    screen = Screen.AnalyticsScreen
                ),
                NavigationItem(
                    title = "Budget",
                    iconFilled = Icons.Filled.AccountBalanceWallet,
                    iconOutlined = Icons.Outlined.AccountBalanceWallet,
                    screen = Screen.BudgetScreen
                ),
                NavigationItem(
                    title = "Profile",
                    iconFilled = Icons.Filled.Person,
                    iconOutlined = Icons.Outlined.Person,
                    screen = Screen.ProfileScreen
                )
            )

            navigationItems.forEach { item ->
                val selected = currentRoute == item.screen.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selected) item.iconFilled else item.iconOutlined,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp),
                            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp
                            ),
                            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                    selected = selected,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
