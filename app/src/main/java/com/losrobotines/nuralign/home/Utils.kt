package com.losrobotines.nuralign.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.ui.theme.mainColor
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigationItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

data class BottomNavigationItemContent(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    screenContent: @Composable () -> Unit,
) {
    val context = LocalContext.current.applicationContext

    val items = listOf(
        BottomNavigationItemContent(
            title = "Asistencia",
            selectedIcon = R.drawable.call_icon,
            unselectedIcon = R.drawable.call_icon
        ),
        BottomNavigationItemContent(
            title = "Inicio",
            selectedIcon = R.drawable.home_icon_screen_selected,
            unselectedIcon = R.drawable.icon_home
        ),
        BottomNavigationItemContent(
            title = "Ajustes",
            selectedIcon = R.drawable.settings_icon,
            unselectedIcon = R.drawable.settings_icon
        )
    )

    var bottomNavState by rememberSaveable { mutableStateOf(0) }

    BottomAppBar(
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp)
    ) {
        BottomNavigation(
            backgroundColor = mainColor,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Bottom)
                .height(70.dp)
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    selected = bottomNavState == index,
                    onClick = {
                        /*
                        when (item.title) {
                            "Asistencia" -> {
                                val intent = Intent(context, Estado::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            }

                            "Inicio" -> {
                                val intent = Intent(context, HomeScreen::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            }

                            "Ajustes" -> {
                                val intent = Intent(context, Medicacion::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            }
                        }

                         */
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = if (bottomNavState == index) item.selectedIcon else item.unselectedIcon),
                            contentDescription = item.title,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 10.dp,end=5.dp),
                            //     alignment = Alignment.Center, colorFilter = ColorFilter.tint(
                            //       mainColor
                            //)
                        )

                    },
                    label = { Text(item.title, color = Color.White) }
                )
            }
        }
    }

    screenContent()
}