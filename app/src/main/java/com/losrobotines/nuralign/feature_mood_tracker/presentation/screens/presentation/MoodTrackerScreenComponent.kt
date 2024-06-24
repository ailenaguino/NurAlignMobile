package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.utils.getDayOfWeek
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.utils.getMonth
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepViewModel
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.Calendar
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoodTrackerScreenComponent(
    navController: NavController,
    moodTrackerViewModel: MoodTrackerViewModel,
) {
    val context = LocalContext.current.applicationContext
    var isVisibleSelectedAnimos by remember { mutableStateOf(false) }
    val isSaved by moodTrackerViewModel.isSaved.observeAsState(false)
    val route by moodTrackerViewModel.route.observeAsState("")
    val isVisible by moodTrackerViewModel.isVisible.observeAsState(false)
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                item {
                    SharedComponents().HalfCircleTop("Seguimiento del\nestado del ánimo")
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
                                "¿Cómo te sentiste hoy?",
                                "Clickeame para esconder mi diálogo"
                            )
                        )
                    }
                }
                item {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Linea()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                item {
                    Box(modifier = Modifier.padding(8.dp)) {
                        AnimoDeprimido(moodTrackerViewModel)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(8.dp)) {
                        AnimoElevado(moodTrackerViewModel)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(8.dp)) {
                        AnimoIrritable(moodTrackerViewModel)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(8.dp)) {
                        AnimoAnsioso(moodTrackerViewModel)
                    }
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                        saveButton(moodTrackerViewModel, context, isVisibleSelectedAnimos)
                    }
                }
            }
            SharedComponents().CompanionCongratulation(isVisible = isVisible) {
                goToNextTracker(
                    navController,
                    route, moodTrackerViewModel
                )
            }
        }
        SnackbarError(moodTrackerViewModel, snackbarHostState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SnackbarError(
    moodTrackerViewModel: MoodTrackerViewModel,
    snackbarHostState: SnackbarHostState
) {
    val errorMessage by moodTrackerViewModel.errorMessage.observeAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            moodTrackerViewModel.clearErrorMessage()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun goToNextTracker(
    navController: NavController,
    route: String,
    moodTrackerViewModel: MoodTrackerViewModel,
) {
    moodTrackerViewModel.setIsVisible(false)
    navController.navigate(route)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun saveButton(
    moodTrackerViewModel: MoodTrackerViewModel,
    context: Context?,
    isVisibleSelectedAnimos: Boolean
) {
    var isVisibleSelectedAnimos1 = isVisibleSelectedAnimos
    Spacer(modifier = Modifier.height(50.dp))
    Button(
        onClick = {
            if (moodTrackerViewModel.irritableValue.intValue == -1 ||
                moodTrackerViewModel.lowestValue.intValue == -1 ||
                moodTrackerViewModel.highestValue.intValue == -1 ||
                moodTrackerViewModel.anxiousValue.intValue == -1
            ) {
                Toast.makeText(context, "Complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                isVisibleSelectedAnimos1 = true
                moodTrackerViewModel.saveData()
                moodTrackerViewModel.checkNextTracker()
            }
        },
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(15.dp),
        enabled = true
    ) {
        Text("Guardar")
    }
    Spacer(modifier = Modifier.height(100.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AnimoDeprimido(moodTrackerViewModel: MoodTrackerViewModel) {
    val colors = listOf(
        Color(0xff9ebadc), Color(0xff678bb7), Color(0xff385f8e),
        Color(0xff1b477c), Color(0xff001e41)
    )
    SelectAnimo(
        "Ánimo más deprimido",
        "deprimido",
        R.drawable.animo_deprimido_additional_note_icon,
        colors,
        moodTrackerViewModel
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AnimoElevado(moodTrackerViewModel: MoodTrackerViewModel) {
    val colors = listOf(
        Color(0xff91c776), Color(0xff67a549), Color(0xff408022),
        Color(0xff29630e), Color(0xff144000)
    )
    SelectAnimo(
        "Ánimo más elevado",
        "elevado",
        R.drawable.animo_elevado_additional_note_icon,
        colors,
        moodTrackerViewModel
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AnimoIrritable(moodTrackerViewModel: MoodTrackerViewModel) {
    val colors = listOf(
        Color(0xffdec278), Color(0xffac914b), Color(0xff7c6529),
        Color(0xff5f4b18), Color(0xff402e00)
    )
    SelectAnimo(
        "Ánimo más irritable",
        "irritable",
        R.drawable.animo_irritable_additional_note_icon,
        colors,
        moodTrackerViewModel
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AnimoAnsioso(moodTrackerViewModel: MoodTrackerViewModel) {
    val colors = listOf(
        Color(0xffc381ba), Color(0xffa05695), Color(0xff813675),
        Color(0xff732166), Color(0xff400036)
    )

    SelectAnimo(
        "Ánimo más ansioso",
        "ansioso",
        R.drawable.animo_ansioso_additional_note_icon,
        colors,
        moodTrackerViewModel
    )
}

@Composable
fun Linea() {
    val calendar = Calendar.getInstance()
    val lineaModifier = Modifier
        .fillMaxWidth()
        .height(4.dp)

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))}," +
                    " ${calendar.get(Calendar.DAY_OF_MONTH)} de" +
                    " ${getMonth(calendar.get(Calendar.MONTH))}",
            color = Color.Black,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 30.dp, end = 8.dp)
        )
        Box(
            modifier = lineaModifier
                .background(mainColor)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAnimo(
    title: String,
    animoType: String,
    iconResId: Int,
    colors: List<Color>,
    moodTrackerViewModel: MoodTrackerViewModel
) {
    val labels = listOf("Nulo", "Leve", "Moderado", "Alto", "Severo")

    var selectedBox by remember { mutableStateOf(-1) }
    var textValue by remember { mutableStateOf("") }
    var isTextFieldVisible by remember { mutableStateOf(false) }

    // Actualizar selectedBox y textValue con los valores del ViewModel
    selectedBox = when (animoType) {
        "elevado" -> moodTrackerViewModel.highestValue.intValue
        "deprimido" -> moodTrackerViewModel.lowestValue.intValue
        "irritable" -> moodTrackerViewModel.irritableValue.intValue
        "ansioso" -> moodTrackerViewModel.anxiousValue.intValue
        else -> -1
    }

    textValue = when (animoType) {
        "elevado" -> moodTrackerViewModel.highestNote.value
        "deprimido" -> moodTrackerViewModel.lowestNote.value
        "irritable" -> moodTrackerViewModel.irritableNote.value
        "ansioso" -> moodTrackerViewModel.anxiousNote.value
        else -> ""
    }

    LaunchedEffect(animoType) {
        // Cada vez que el valor de animoType cambie, se ejecuta
        selectedBox = when (animoType) {
            "elevado" -> moodTrackerViewModel.highestValue.intValue
            "deprimido" -> moodTrackerViewModel.lowestValue.intValue
            "irritable" -> moodTrackerViewModel.irritableValue.intValue
            "ansioso" -> moodTrackerViewModel.anxiousValue.intValue
            else -> -1
        }

        textValue = when (animoType) {
            "elevado" -> moodTrackerViewModel.highestNote.value
            "deprimido" -> moodTrackerViewModel.lowestNote.value
            "irritable" -> moodTrackerViewModel.irritableNote.value
            "ansioso" -> moodTrackerViewModel.anxiousNote.value
            else -> ""
        }
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .requiredWidth(378.dp)
            .requiredHeight(100.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Text(
            text = title,
            color = colors[3],
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 15.dp, top = 7.dp)
        )
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = ".",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .requiredSize(45.dp)
                .padding(end = 8.dp, bottom = 12.dp)
                .clickable { isTextFieldVisible = !isTextFieldVisible }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .border(
                    BorderStroke(2.dp, colors[3]),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(start = 15.dp, bottom = 4.dp, top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            colors.forEachIndexed { index, color ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .requiredWidth(48.dp)
                            .requiredHeight(33.dp)
                            .background(color)
                            .border(
                                width = if (index == selectedBox) 3.dp else 0.dp,
                                color = if (index == selectedBox) Color.Yellow else Color.Transparent,
                            )
                            .clickable(enabled = true) {
                                selectedBox = index
                                val selectedValue = labels[index]
                                when (animoType) {
                                    "elevado" -> moodTrackerViewModel.highestValue.value =
                                        labels.indexOf(selectedValue)

                                    "deprimido" -> moodTrackerViewModel.lowestValue.value =
                                        labels.indexOf(selectedValue)

                                    "irritable" -> moodTrackerViewModel.irritableValue.value =
                                        labels.indexOf(selectedValue)

                                    "ansioso" -> moodTrackerViewModel.anxiousValue.value =
                                        labels.indexOf(selectedValue)
                                }
                            }
                    )
                    Text(
                        text = labels[index],
                        color = colors[3],
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }
        }
        if (isTextFieldVisible) {
            TextField(
                value = textValue,
                onValueChange = {
                    textValue = it
                    when (animoType) {
                        "elevado" -> moodTrackerViewModel.highestNote.value = it
                        "deprimido" -> moodTrackerViewModel.lowestNote.value = it
                        "irritable" -> moodTrackerViewModel.irritableNote.value = it
                        "ansioso" -> moodTrackerViewModel.anxiousNote.value = it
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 45.dp, bottom = 12.dp),
                label = { Text("Nota adicional") },
                singleLine = true,
                enabled = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray,
                    focusedLabelColor = secondaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
}
