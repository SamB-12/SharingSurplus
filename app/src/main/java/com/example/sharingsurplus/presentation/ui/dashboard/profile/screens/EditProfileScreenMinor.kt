package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.states.dashboard.profile.EditProfileScreenUiState
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun EditProfileScreenMinor(
    modifier: Modifier = Modifier,
    name: String = "",
    email: String = "",
    phone: String = "",
    address: String = "",
    isDialogVisible: Boolean = false,
    isBackDialogVisible: Boolean = false,
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPhoneChanged: (String) -> Unit = {},
    onAddressChanged: (String) -> Unit = {},
    onShowDialog: () -> Unit = {},
    onHideDialog: () -> Unit = {},
    onConfirmUpdate: () -> Unit = {},
    onCancelUpdate: () -> Unit = {},
    onConfirmBack: () -> Unit = {},
    onCancelBack: () -> Unit = {},
    navigateTo: () -> Unit = {},
    isSuccess: Result<User>? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val uiState = EditProfileScreenUiState("Phil K", "philk@gmail.com", "123456", "address")
        //val uiState by editProfileViewModel.editProfileUiState.collectAsState()
        val context = LocalContext.current
        val painter: Painter = painterResource(id = R.drawable.ic_sharing_surplus_logo)

        Image(painter = painter, contentDescription = "Logo", modifier = modifier.padding(top = 12.dp))//change it to have a edit icon and make it clickable
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Edit your profile details", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Name", value = name, onValueChanged = { onNameChanged(it) })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Email", value = email, onValueChanged = { onEmailChanged(it) })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Phone", value = phone, onValueChanged = { onPhoneChanged(it) })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(label = "Address", value = address, onValueChanged = { onAddressChanged(it) })
        Spacer(modifier = Modifier.height(40.dp))
        ButtonComponent(onClick = { onShowDialog() }, text = "Confirm details") //add a alert dialog when pressed
        Spacer(modifier = Modifier.height(16.dp))

        if (isDialogVisible){
            ConfirmationDialogComponent(
                title = "Confirm Changes?",
                message = "Are you sure you want to save the changes?",
                onConfirm = {onConfirmUpdate()},
                onCancel = {onCancelUpdate()}
            )
        }

        if (isBackDialogVisible){
            ConfirmationDialogComponent(
                title = "Cancel Changes?",
                message = "Are you sure you want to cancel the changes?",
                onConfirm = {onConfirmBack()},
                onCancel = {onCancelBack()}
            )
        }

        LaunchedEffect(isSuccess) {
            when (isSuccess) {

                is Result.Success -> {
                    Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                    navigateTo()
                }
                is Result.Error -> {
                    Toast.makeText(context, (uiState.isSuccess as Result.Error).message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Nothing
                }
            }
        }

        if (uiState.isLoading){
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenMinorPreview() {
    EditProfileScreenMinor()
}