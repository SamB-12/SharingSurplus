package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    label: String,
    value: String = "",
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
    error: Boolean = false,
    errorMessage: String = "Invalid value",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false
) {

    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp) // adding some space to the label
                .background(
                    color = Color.White,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(8.dp)
                )
        )
        // OutlineTextField will be the content...
        OutlinedTextField(
            value = value,
            onValueChange = {text ->
                onValueChanged(text)
            },
            label = { Text(label) },
            modifier = modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SecondaryColor,
                focusedLabelColor = SecondaryColor,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            ),
            isError = error,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (error) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Error Icon",
                    )
                }
            },
            readOnly = readOnly
        )
    }
    if (error) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldComponentPreview() {
    TextFieldComponent("Username")
}