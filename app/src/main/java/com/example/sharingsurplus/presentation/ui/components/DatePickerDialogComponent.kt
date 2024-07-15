package com.example.sharingsurplus.presentation.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SharingSurplusTheme
import com.example.sharingsurplus.presentation.utils.parseDateString
import java.util.Calendar
import java.util.Locale

@Composable
fun DatePickerDialogComponent(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    dateSelected: String = "",
    onDateSelectedChanged: (String) -> Unit = {},
    label: String = "Select Pickup Date",
    maxDate: String = "",
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val maxDateInMillis = parseDateString(maxDate)

    val datePickerDialog = DatePickerDialog(
        context,
        //R.style.CustomDatePickerDialogTheme,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelectedChanged(selectedDate)
        }, year, month, day
    )

    maxDateInMillis?.let {maxDate ->
        datePickerDialog.datePicker.maxDate = maxDate
    }

    OutlinedTextField(
        value = dateSelected,
        onValueChange = { date ->
            onDateSelectedChanged(date)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = PrimaryColor)
            .clickable {
                Log.i("This was clicked?", "YES INDEED")
                datePickerDialog.show()
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
}

@Preview(showBackground = true)
@Composable
private fun DatePickerDialogComponentPreview() {
    SharingSurplusTheme {
        DatePickerDialogComponent(maxDate = "24/07/2024")
    }
}