package com.example.sharingsurplus.data.model

data class Post(
    val postId:String = "",
    val userId:String = "",
    val userName:String = "",
    val postTitle:String = "",
    val postContent:String = "",
    val timestamp:Long = System.currentTimeMillis(),
)
