package com.example.sharingsurplus.data.states.dashboard.community_forum

import com.example.sharingsurplus.data.model.Post
import com.example.sharingsurplus.data.repository.AuthResult

data class CommunityForumUiState(
    val posts: List<Post> = emptyList(),
    val isNewPostDialogVisible: Boolean = false,
    val userId: String = "",
    val userName: String = "",
    val postTitle: String = "",
    val postContent: String = "",
    val uploadResult: AuthResult<Unit>?= null,
)
