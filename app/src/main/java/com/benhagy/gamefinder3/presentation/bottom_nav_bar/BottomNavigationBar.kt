package com.benhagy.gamefinder3.presentation.bottom_nav_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.benhagy.gamefinder3.presentation.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            DestinationsNavHost(
                navController = navController,
                navGraph = NavGraphs.root
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination: NavDestination? = navController.currentDestination

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
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
                        restoreState =
                            destination.direction != BottomNavItem.BookmarksScreen.direction
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {}
                    ) {
                        Icon(
                            destination.icon,
                            contentDescription = stringResource(destination.label),
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(destination.label),
                    )
                }
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