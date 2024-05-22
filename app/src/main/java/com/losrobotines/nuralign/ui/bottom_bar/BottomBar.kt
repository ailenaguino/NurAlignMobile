package com.losrobotines.nuralign.ui.bottom_bar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


sealed class Destinations(
    val route: String,
    val title: String,
    val icon: Int
) {
    data object Asistencia : Destinations(
        route = "",
        title = "Asistencia",
        icon = R.drawable.call_icon
    )

    data object Home : Destinations(
        route = Routes.HomeScreen.route,
        title = "Home",
        icon = R.drawable.icon_home
    )

    data object Configuracion : Destinations(
        route = Routes.SettingsScreen.route,
        title = "Configuracion",
        icon = R.drawable.settings_icon
    )

    @Composable
    fun BottomBarNavigation(
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current

        val screens = listOf(
            Asistencia, Home, Configuracion
        )

        NavigationBar(
            modifier = modifier,
            containerColor = mainColor,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.title,
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 5.dp)
                                .align(Alignment.CenterVertically)
                                .background(Color.Transparent)
                        )
                    },
                    /*label = {
                        Text(
                            text = screen.title,
                            modifier = Modifier.padding(top = 69.dp, end = 5.dp)
                        )
                    },

                     */
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (screen == Destinations.Asistencia) {
                            val callIntent: Intent = Uri.parse("tel:111").let { number ->
                                Intent(Intent.ACTION_DIAL, number)
                            }
                            context.startActivity(callIntent)
                        } else {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                navController.navigate(Home.route)
                            }
                        }

                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = secondaryColor,
                        selectedIconColor = Color.White,
                        unselectedTextColor = Color.White
                    ),

                    )
            }
        }
    }
}

