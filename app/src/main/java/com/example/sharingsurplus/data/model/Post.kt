package com.example.sharingsurplus.data.model

/**
 * This is data class for Post.
 */
data class Post(
    val postId:String = "",
    val userId:String = "",
    val userName:String = "",
    val postTitle:String = "",
    val postContent:String = "",
    val timestamp:Long = System.currentTimeMillis(),
)
