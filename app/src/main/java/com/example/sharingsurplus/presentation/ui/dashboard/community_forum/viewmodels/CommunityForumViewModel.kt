package com.example.sharingsurplus.presentation.ui.dashboard.community_forum.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharingsurplus.data.model.Post
import com.example.sharingsurplus.data.repository.Result
import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.states.dashboard.community_forum.CommunityForumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This ViewModel is responsible for managing the state of the Community Forum screen.
 */
@HiltViewModel
class CommunityForumViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _communityForumState = MutableStateFlow(CommunityForumUiState())
    val communityForumState = _communityForumState.asStateFlow()

    init {
        fetchUserId()
        fetchPosts()
    }

    private fun fetchUserId() {
        _communityForumState.value = _communityForumState.value.copy(userId = authRepository.currentUser!!.uid)
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            firestoreRepository.getPosts().collectLatest { postsList -> //this is supposed to update in realtime
                Log.d("CommunityForumViewModel", "Posts fetched: $postsList")
                _communityForumState.value = _communityForumState.value.copy(posts = postsList)
            }
        }
    }

    fun isNewPostDialogVisible(isVisible: Boolean) {
        _communityForumState.value = _communityForumState.value.copy(isNewPostDialogVisible = isVisible)
    }

    fun onNewPostTitleChange(title: String) {
        _communityForumState.value = _communityForumState.value.copy(postTitle = title)
    }

    fun onNewPostContentChange(content: String) {
        _communityForumState.value = _communityForumState.value.copy(postContent = content)
    }

    fun addNewPost() {
        if (validateInput(_communityForumState.value.postTitle, _communityForumState.value.postContent)){
            viewModelScope.launch {
                val result = firestoreRepository.getUser(_communityForumState.value.userId)

                if (result is Result.Success) {
                    val user = result.data

                    _communityForumState.value = _communityForumState.value.copy(userName = user.name)

                    val newPost = Post(
                        postTitle = _communityForumState.value.postTitle,
                        postContent = _communityForumState.value.postContent,
                        userName = _communityForumState.value.userName,
                        userId = _communityForumState.value.userId
                    )

                    firestoreRepository.addPost(newPost)

                    _communityForumState.value = _communityForumState.value.copy(uploadResult = Result.Success(Unit))
                }
            }
        } else {
            _communityForumState.value = _communityForumState.value.copy(uploadResult = Result.Error("Invalid input"))
        }

    }

    fun validateInput(title: String, content: String): Boolean{
        if (title.isEmpty() || content.isEmpty()){
            return false
        } else {
            return true
        }
    }

}