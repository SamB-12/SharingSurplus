package com.example.sharingsurplus.presentation.ui.dashboard.community_forum.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.states.dashboard.community_forum.CommunityForumUiState
import com.example.sharingsurplus.presentation.ui.components.AddNewPostDialogComponent
import com.example.sharingsurplus.presentation.ui.components.AddPostButtonComponent
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.CommunityFormPostComponent
import com.example.sharingsurplus.presentation.ui.dashboard.community_forum.viewmodels.CommunityForumViewModel
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun CommunityForumScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    communityFormViewModel: CommunityForumViewModel = hiltViewModel()
) {

    val uiState by communityFormViewModel.communityForumState.collectAsState()

    val localContext = LocalContext.current


    LaunchedEffect(uiState.uploadResult) {
        when (uiState.uploadResult) {
            is AuthResult.Success -> {
                Toast.makeText(localContext, "Posted Successfully", Toast.LENGTH_SHORT).show()
            }
            is AuthResult.Error -> {
                Toast.makeText(localContext, (uiState.uploadResult as AuthResult.Error).message?:"unknown error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //Nothing
            }
        }
    }


    if (uiState.isNewPostDialogVisible){
        AddNewPostDialogComponent(
            title = "Add New Post",
            message1 = "Title",
            message2 = "Description",
            onCancel = { communityFormViewModel.isNewPostDialogVisible(false)},
            onConfirm = {
                communityFormViewModel.isNewPostDialogVisible(false)
                communityFormViewModel.onNewPostTitleChange("")
                communityFormViewModel.onNewPostContentChange("")
                communityFormViewModel.addNewPost()
            },
            onPostContentChange = { communityFormViewModel.onNewPostContentChange(it)},
            onPostTitleChange = { communityFormViewModel.onNewPostTitleChange(it)},
            postTitle = uiState.postTitle,
            postContent = uiState.postContent
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = PrimaryColor),
                //.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.posts.isEmpty()){
                item {
                    Text(text = "No posts to show", color = PrimaryTextColor, textAlign = TextAlign.Center)
                }
            }

            uiState.posts.forEach { post ->
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    CommunityFormPostComponent(
                        postTitle = post.postTitle,
                        postDescription = post.postContent,
                        postedBy = post.userName
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            AddPostButtonComponent(
                onClick = {communityFormViewModel.isNewPostDialogVisible(true)},
                text = "Add Post"
            )
        }
    }
}

@Preview
@Composable
private fun CommunityForumScreenPreview() {
    CommunityForumScreen()
}