package com.example.sharingsurplus.presentation.ui.components

import TimePickerDialogComponent
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor
import com.example.sharingsurplus.presentation.ui.theme.TeritiaryColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker2(
    modifier: Modifier = Modifier,
    timeSelected: String = "",
    onTimeSelectedChanged: (String) -> Unit = {},
    label: String = "Select Time",
    isTimePickerDialogVisible: Boolean = false,
    timePickerDialogChanged: (Boolean) -> Unit = {},
) {

    //var isTimePickerDialogVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = timeSelected,
        onValueChange = { time ->
            onTimeSelectedChanged(time)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = PrimaryColor)
            .clickable {
                Log.i("This was clicked?", "YES INDEED")
                if (isTimePickerDialogVisible) {
                    timePickerDialogChanged(false)
                } else {
                    timePickerDialogChanged(true)
                }
            }
        ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SecondaryColor,
            focusedLabelColor = SecondaryColor,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        readOnly = true,
        singleLine = true,
        trailingIcon = {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_clock_24), contentDescription = "Clock")
        },
        enabled = false
    )

    if (isTimePickerDialogVisible) {
        val timePickerState = rememberTimePickerState()
        
        TimePickerDialogComponent(
            onCancel = {timePickerDialogChanged(false)},
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                cal.set(Calendar.MINUTE, timePickerState.minute)
                cal.isLenient = false

                val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)
                onTimeSelectedChanged(formattedTime)

                timePickerDialogChanged(false)
            }
        ) {
            TimePicker(
                state = timePickerState,
                modifier = Modifier,
                colors = TimePickerDefaults.colors(
                    containerColor = PrimaryColor,
                    clockDialColor = PrimaryColor,
                    clockDialSelectedContentColor = PrimaryTextColor,
                    clockDialUnselectedContentColor = SecondaryTextColor,
                    selectorColor = SecondaryColor,
                    periodSelectorSelectedContainerColor = SecondaryColor,
                    timeSelectorSelectedContainerColor = SecondaryColor,
                    timeSelectorUnselectedContainerColor = Color.LightGray,
                    periodSelectorUnselectedContainerColor = Color.LightGray
                )
            )
        }
    }
}

@Preview
@Composable
private fun TimePicker2Preview() {


    TimePicker2(
        timeSelected = "12:00",
        onTimeSelectedChanged = {
        },
        label = "Select Time",
    )
}