package com.losrobotines.nuralign.feature_companion.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_companion.presentation.utils.RobotinStyles
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.COMPANION
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


@Composable
fun CompanionScreenComponent(companionViewModel: CompanionViewModel) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val currentStyle = preferencesManager.getInt(COMPANION, R.drawable.robotin_bebe)
    val currentStyleViewModel: Int by companionViewModel.currentStyle.observeAsState(R.drawable.robotin_bebe)

    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            item {
                SharedComponents().HalfCircleTop("Mi acompañante")
            }
            item {
                LargeFloatingActionButton(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    containerColor = mainColor,
                    modifier = Modifier.padding(8.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 7.dp
                    )
                ) {
                    SharedComponents().fabCompanion(
                        listOf(
                            "Acá vas a poder elegir cómo querés que me vea"
                        )
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CurrentStyle(currentStyleViewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = secondaryColor, thickness = 2.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        OtherStyles(companionViewModel)
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                onClick = {companionViewModel.setCurrentStyle(companionViewModel.isSelected.value!!)
                          preferencesManager.saveIntData(COMPANION, companionViewModel.isSelected.value!!)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor
                ),
                enabled = true
            ) {
                Text(text = "Guardar")
            }
        }
    }
}

@Composable
fun CurrentStyle(currentStyle: Int) {
    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(mainColor)
            .padding(8.dp)
    ) {

        Image(
            painterResource(id = currentStyle),
            contentDescription = "Acompañante",
            modifier = Modifier
                .size(120.dp)
                .padding(end = 1.dp, start = 4.dp)
        )
    }
}

@Composable
fun OtherStyles(viewModel: CompanionViewModel) {

    val listStyles = listOf(
        RobotinStyles(R.drawable.robotin_bebe, false),
        RobotinStyles(R.drawable.robotin_bebe_contento_invierno, false),
        RobotinStyles(R.drawable.robotin_bebe_contento_navidad, false)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listStyles.size) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .size(110.dp)
                    .wrapContentSize(Alignment.Center)
                    .clickable(onClick = {
                        viewModel.setIsSelected(listStyles[it].style)
                    }),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(id = listStyles[it].style),
                    contentDescription = "Acompañante",
                    modifier = Modifier
                        .size(85.dp)
                )
            }
        }
    }
}
