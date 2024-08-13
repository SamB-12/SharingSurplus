package com.example.sharingsurplus.presentation.ui.dashboard.profile.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.profile.viewmodels.EditProfileViewModel

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