package com.losrobotines.nuralign.ui.bottom_bar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val color: Color
) {
    data object Asistencia : Destinations(
        route = "",
        title = "Asistencia",
        icon = Icons.Rounded.Call,
        color = Color(0xFF620000)
    )

    data object Home : Destinations(
        route = Routes.HomeScreen.route,
        title = "Home",
        icon = Icons.Rounded.Home,
        color = secondaryColor
    )

    data object Configuracion : Destinations(
        route = Routes.SettingsScreen.route,
        title = "Configuración",
        icon = Icons.Rounded.Settings,
        color = secondaryColor
    )

    @Composable
    fun BottomBarNavigation(
        navController: NavHostController,
        modifier: Modifier = Modifier,
    ) {
        val context = LocalContext.current

        val screens = listOf(
            Asistencia, Home, Configuracion
        )

        NavigationBar(
            modifier = Modifier
                .height(65.dp)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
            containerColor = mainColor,
            tonalElevation = 20.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screens.forEach { screen ->
                val selected = currentRoute == screen.route
                NavigationBarItem(
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = "Inicio",
                                tint = if (selected) Color.White else screen.color,
                                modifier = Modifier
                                    .size(35.dp)
                            )
                            Text(
                                text = screen.title,
                                fontSize = 16.sp,
                                color = if (selected) Color.White else screen.color
                            )
                        }
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (screen == Destinations.Asistencia) {
                            val callIntent: Intent = Uri.parse("tel:0800-333-1665").let { number ->
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
                        selectedIconColor = secondaryColor,
                        indicatorColor = mainColor
                    ),

                    )
            }
        }
    }
}

