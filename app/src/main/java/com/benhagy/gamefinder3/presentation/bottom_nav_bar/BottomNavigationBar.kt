package com.benhagy.gamefinder3.presentation.bottom_nav_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.benhagy.gamefinder3.presentation.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination: NavDestination? = navController.currentDestination

    BottomAppBar {
        BottomNavItem.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction.route) {
                        launchSingleTop = true
                        val navigationRoutes = BottomNavItem.values()

                        val firstBottomBarDestination = navController.backQueue
                            .firstOrNull { navBackStackEntry ->
                                checkForDestinations(
                                    navigationRoutes,
                                    navBackStackEntry
                                )
                            }
                            ?.destination
                        // remove all navigation items from the stack
                        // so only the currently selected screen remains in the stack
                        if (firstBottomBarDestination != null) {
                            popUpTo(firstBottomBarDestination.id) {
                                inclusive = true
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = destination.label,
                        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                    )
                },
                label = {
                    Text(
                        text = destination.label,
                        color = Color.White
                ) },
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onSecondaryContainer)
            // color of nav items?
            )
        }
    }
}

fun checkForDestinations(
    navigationRoutes: Array<BottomNavItem>,
    navBackStackEntry: NavBackStackEntry
): Boolean {
    navigationRoutes.forEach {
        if (it.direction.route == navBackStackEntry.destination.route) {
            return true
        }

    }
    return false
}