package com.losrobotines.nuralign.feature_routine.presentation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GenericActivityRow(activity: Activity, routineViewModel: RoutineViewModel) {
    val isSaved by routineViewModel.isSaved.observeAsState(false)
    var showEditDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                "${activity.name} - ${activity.time}",
                modifier = Modifier
                    .fillMaxWidth(),
                color = secondaryColor,
                fontSize = 24.sp
            )
        }

        IconButton(onClick = {
            showEditDialog = true
        }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = secondaryColor)
        }

        if (showEditDialog) {
            EditActivityAlertDialog(
                onDismissRequest = { showEditDialog = false },
                confirmButton = { showEditDialog = false },
                activity = activity,
                routineViewModel = routineViewModel
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectDaysForActivity(activity: Activity, routineViewModel: RoutineViewModel) {
    val isSaved by routineViewModel.isSaved.observeAsState(false)
    val days = arrayOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

    Column {
        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                Text(
                    text = day,
                    color = secondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1.4f)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                val isSelected = activity.days.contains(day)
                Button(
                    onClick = {
                        if (isSelected) {
                            routineViewModel.removeSelectedDayFromActivity(activity, day)
                        } else {
                            routineViewModel.addSelectedDayToActivity(activity, day)
                        }
                    },
                    shape = CutCornerShape(10),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) secondaryColor else mainColor,
                    ),
                    modifier = Modifier
                        .weight(1.4f)
                        .height(24.dp)
                        .padding(horizontal = 1.dp)
                        .defaultMinSize(12.dp),
                    enabled = true
                ) {
                    Text(text = " ")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider(color = secondaryColor, thickness = 2.dp)
    }
}