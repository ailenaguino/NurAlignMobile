package com.losrobotines.nuralign.feature_home.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_home.presentation.utils.HomeItemData
import com.losrobotines.nuralign.navigation.MainActivity
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

class TutorialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ElementsList()
                }
            }
        }
    }
}

@Preview
@Composable
fun ElementsList() {
    val itemsList = listOf(
        HomeItemData.Mood,
        HomeItemData.Medication,
        HomeItemData.Sleep,
        HomeItemData.Therapy,
        HomeItemData.WeeklySummary,
        HomeItemData.Companion,
        HomeItemData.Achievement,
        HomeItemData.Routine
    )
    val navController: NavHostController = rememberNavController()
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { RobotinTextBubble() }
        itemsList.map { item { FeatureElement(it.name, it.image, it.desc) } }
        item {
            Button(
                onClick = { context.startActivity(Intent(context, MainActivity::class.java)) },
                colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
            ) {
                Text(text = "¡Ya entendí! Quiero ir al Inicio")
            }
        }
    }
}

@Composable
fun FeatureElement(name: String, icon: ImageVector, desc: String) {
    var isChecked by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icono",
                tint = secondaryColor,
                modifier = Modifier.size(50.dp)
            )
            Text(text = name, fontSize = 24.sp, color = secondaryColor)
            Icon(
                imageVector = Icons.Filled.ArrowDropDownCircle,
                contentDescription = null,
                tint = secondaryColor,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { isChecked = !isChecked }
            )
            //Switch(checked = isChecked, onCheckedChange = {isChecked = it})
        }

        Divider(thickness = 4.dp, color = secondaryColor)

        AnimatedVisibility(visible = isChecked) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = desc,
                        fontSize = 20.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }

}


@Composable
fun RobotinTextBubble() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .clip(shape = CircleShape)
                .background(secondaryColor)
                .size(70.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Image(
                painterResource(id = R.drawable.robotin_bebe_contento),
                contentDescription = "Robotín",
                modifier = Modifier
                    .size(70.dp)
                    .padding(4.dp)
            )
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Clickeá en cada opción para saber que hace cada una.",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

