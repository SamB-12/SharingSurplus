package com.example.sharingsurplus.data.repository

/**
 * This is a generic sealed class that represents the result of an operation.
 */
sealed class Result<out T> { // we add this class to handle the results
    data class Success<T>(val data: T) : Result<T>() //this data class will be used to handle the s
    //basically whenever the the result of a callback is success, it will return the data
    //we request the data as a type T - which is a generic type of any, so it can be any type we specify
    //in our case, it is the firebase user object
    data class Error(val message: String) : Result<Nothing>()//this data class will be used to handle the error results

    object Loading: Result<Nothing>()//this object will be used to handle the loading state

}