
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor

@Composable
fun PasswordFieldComponent(
    label: String,
    value: String = "",
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
    error: Boolean = false,
    errorMessage: String = "Invalid value",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {

    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }

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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) R.drawable.ic_visibility_on_24 else R.drawable.ic_visibility_off_24
                    Icon(painter = painterResource(id = icon), contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            }
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
fun PasswordFieldComponentPreview() {
    PasswordFieldComponent(label = "Password")
}