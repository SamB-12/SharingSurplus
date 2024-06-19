package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun TermsAndConditionsComponent(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {

    var checked by remember { mutableStateOf(isChecked)}

    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(it)
            },
            colors = CheckboxDefaults.colors(checkedColor = SecondaryColor)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "I agree to the terms and conditions", style = MaterialTheme.typography.bodyLarge, color = SecondaryTextColor)
    }
}

@Preview
@Composable
private fun TermsAndConditionsComponentPreview() {
    TermsAndConditionsComponent(isChecked = false, onCheckedChange = {})
}