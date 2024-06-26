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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.presentation.navigation.utils.Graphs
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.EditProfileViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavController?
) {

    val uiState by editProfileViewModel.editProfileUiState.collectAsState()

    ScaffoldComponent(
        modifier = modifier,
        topBar = { TopAppBarWithBackComponent(title = "Edit Profile", onBackClick = {editProfileViewModel.pressBack()})},
        content = {
            EditProfileScreenMinor(
                name = uiState.name,
                email = uiState.email,
                phone = uiState.phone,
                address = uiState.address,
                isDialogVisible = uiState.isAlertDialogVisible,
                isBackDialogVisible = uiState.isBackPressed,
                onNameChanged = {editProfileViewModel.onNameChanged(it)},
                onEmailChanged = {editProfileViewModel.onEmailChanged(it)},
                onPhoneChanged = {editProfileViewModel.onPhoneChanged(it)},
                onAddressChanged = {editProfileViewModel.onAddressChanged(it)},
                onShowDialog = {editProfileViewModel.showDialog()},
                onHideDialog = {editProfileViewModel.hideDialog()},
                onConfirmUpdate = {editProfileViewModel.updateProfile()},
                onCancelUpdate = {editProfileViewModel.hideDialog()},
                onConfirmBack = {navController?.navigateUp()},
                onCancelBack = {editProfileViewModel.cancelBack()},
                navigateTo = {navController?.navigateUp()},
                isSuccess = uiState.isSuccess
            )
        }
    )

}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreen(navController = null)
}