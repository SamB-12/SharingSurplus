package com.example.sharingsurplus.data.model

/**
 * This is data class for User.
 */
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profilePictureUrl : String ?= null,
    val phone : String ?= null,
    val address : String ?= null,
    val karmaPoints : Int = 0,
    val createdAt : Long = System.currentTimeMillis()
)
