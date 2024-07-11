package com.example.sharingsurplus.presentation.ui.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.AccentColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.utils.showDatePickerDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSelectorTextFieldComponent(
    modifier: Modifier = Modifier,
    bestBeforeDate: String = "",
    onBestBeforeDateChanged: (String) -> Unit = {},
    isDatePickerDialogVisible: Boolean = false,
    datePickerDialogChanged: (Boolean) -> Unit = {},
    label: String = "Best Before Date",
) {


    OutlinedTextField(
        value = bestBeforeDate,
        onValueChange = { text ->
            onBestBeforeDateChanged(text)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = PrimaryColor)
            .clickable {
                Log.i("This was clicked?", "YES INDEED")
                if (isDatePickerDialogVisible) {
                    datePickerDialogChanged(false)
                } else {
                    datePickerDialogChanged(true)
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
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
        },
        enabled = false
    )

    if (isDatePickerDialogVisible) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = PrimaryColor
            ),
            onDismissRequest = { datePickerDialogChanged(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerDialogChanged(false)
                        datePickerState.selectedDateMillis?.let { selectedMillis ->
                            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedMillis))
                            onBestBeforeDateChanged(selectedDate)
                        } ?: run {
                            //Toast.makeText(context, "Please select a date", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("OK", color = SecondaryColor)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { datePickerDialogChanged(false) }
                ) {
                    Text("Cancel", color = AccentColor)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarSelectorTextFieldComponentPreview() {
    CalendarSelectorTextFieldComponent()
}