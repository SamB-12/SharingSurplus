package com.example.sharingsurplus.presentation.ui.dashboard.requests.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.presentation.navigation.utils.Routes
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels.RequestSentViewModel
import com.example.sharingsurplus.presentation.utils.GlobalVariables

@Composable
fun RequestSentScreen(
    modifier: Modifier = Modifier,
    requestSentViewModel: RequestSentViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val localContext = LocalContext.current

    val uiState by requestSentViewModel.requestSentUiState.collectAsState()

    LaunchedEffect(uiState.deleteStatus) {
        when (uiState.deleteStatus){
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Request Deleted", Toast.LENGTH_SHORT).show()
            }
            is AuthResult.Error -> {
                Toast.makeText(localContext, (uiState.deleteStatus as AuthResult.Error).message?:"Unknown error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Nothing
            }
        }
    }

    if (uiState.isDeleteDialogVisible){
        ConfirmationDialogComponent(
            title = "Delete Request?",
            message = "Are you sure you want to delete this request?",
            onConfirm = {
                requestSentViewModel.onDeleteRequestDialogChange(false)
                requestSentViewModel.deleteRequest()
                        },
            onCancel = { requestSentViewModel.onDeleteRequestDialogChange(false) }
        )
    }

    if (uiState.isEditDialogVisible){
        ConfirmationDialogComponent(
            title = "Edit Request?",
            message = "Are you sure you want to edit this request?",
            onConfirm = {
                GlobalVariables.requestId = uiState.selectedRequest!!.requestId
                GlobalVariables.produceIdToView = uiState.selectedRequest!!.produceId
                requestSentViewModel.onEditRequestDialogChange(false)
                navController?.navigate(Routes.RequestEdit.route)
            },
            onCancel = { requestSentViewModel.onEditRequestDialogChange(false) }
        )
    }

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Request Sent", onBackClick = {navController?.navigateUp()})},
        content = {
            RequestSentScreenMinor(
                requestList = uiState.requestList,
                onDeleteClick = { request ->
                    requestSentViewModel.onDeleteRequestDialogChange(true)
                    requestSentViewModel.onSelectedRequest(request)
                },
                onEditClick = {request ->
                    requestSentViewModel.onEditRequestDialogChange(true)
                    requestSentViewModel.onSelectedRequest(request)
                }
            )
        }
    )
}

@Preview
@Composable
private fun RequestSentScreenPreview() {
    RequestSentScreen(navController = null)
}