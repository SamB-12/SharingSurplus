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
import com.example.sharingsurplus.presentation.ui.components.ConfirmationDialogComponent
import com.example.sharingsurplus.presentation.ui.components.RequestResponseComponent
import com.example.sharingsurplus.presentation.ui.components.ScaffoldComponent
import com.example.sharingsurplus.presentation.ui.components.TopAppBarWithBackComponent
import com.example.sharingsurplus.presentation.ui.dashboard.requests.viewmodels.RequestReceivedViewModel

@Composable
fun RequestReceivedScreen(
    modifier: Modifier = Modifier,
    requestReceivedViewModel: RequestReceivedViewModel = hiltViewModel(),
    navController: NavHostController?
) {

    val localContext = LocalContext.current

    val uiState by requestReceivedViewModel.requestReceivedUiState.collectAsState()

    LaunchedEffect(uiState.rejectResult) {
        when (uiState.rejectResult) {
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Request Rejected", Toast.LENGTH_SHORT).show()
            }
            is AuthResult.Error -> {
                Toast.makeText(localContext, (uiState.rejectResult as AuthResult.Error).message?:"Unknown Error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Nothing
            }
        }
    }

    LaunchedEffect(uiState.acceptResult) {
        when (uiState.acceptResult) {
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Request Accepted", Toast.LENGTH_SHORT).show()
            }

            is AuthResult.Error -> {
                Toast.makeText(
                    localContext,
                    (uiState.acceptResult as AuthResult.Error).message ?: "Unknown Error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                //Nothing
            }
        }
    }

    if (uiState.isRejectDialogVisible){
        RequestResponseComponent(
            title = "Reject Request?",
            message = "Are you sure you want to reject this request?",
            onConfirm = {
                requestReceivedViewModel.onRejectDialogVisibleChanged(false)
                requestReceivedViewModel.onRejectRequest()
            },
            onCancel = {
                requestReceivedViewModel.onRejectDialogVisibleChanged(false)
            },
            reason = uiState.reason,
            onReasonChange = {
                requestReceivedViewModel.onReasonChanged(it)
            }
        )
    }

    if (uiState.isAcceptDialogVisible){
        RequestResponseComponent(
            title = "Accept Request?",
            message = "Are you sure you want to Accept this request?",
            onConfirm = {
                requestReceivedViewModel.onAcceptDialogVisibleChanged(false)
                requestReceivedViewModel.onAcceptRequest()
            },
            onCancel = {
                requestReceivedViewModel.onAcceptDialogVisibleChanged(false)
            },
            reason = uiState.reason,
            onReasonChange = {
                requestReceivedViewModel.onReasonChanged(it)
            }
        )
    }

    ScaffoldComponent(
        modifier = Modifier,
        topBar = { TopAppBarWithBackComponent(title = "Request Received", onBackClick = {navController?.navigateUp()})},
        content = {
            RequestReceivedScreenMinor(
                requestList = uiState.requestList,
                onAcceptSelected = {request ->
                    requestReceivedViewModel.onAcceptDialogVisibleChanged(true)
                    requestReceivedViewModel.onSelectedRequest(request)
                },
                onDeclineSelected = {request ->
                    requestReceivedViewModel.onRejectDialogVisibleChanged(true)
                    requestReceivedViewModel.onSelectedRequest(request)
                }
            )
        }
    )
}

@Preview
@Composable
private fun RequestReceivedScreenPreview() {
    RequestReceivedScreen(navController = null)
}