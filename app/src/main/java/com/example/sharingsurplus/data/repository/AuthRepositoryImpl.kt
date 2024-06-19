package com.example.sharingsurplus.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, //it gets injected by dagger which finds def from module
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): AuthResult<FirebaseUser> {
        return suspendCancellableCoroutine<AuthResult<FirebaseUser>> { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    continuation.resume(AuthResult.Success(firebaseAuth.currentUser!!))
                } else {
                    continuation.resume(AuthResult.Error(task.exception!!.message!!))
                }
            }
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthResult<FirebaseUser> {
        return suspendCancellableCoroutine {continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    user!!.updateProfile(userProfileChangeRequest).addOnCompleteListener {task -> // this sets up the profile details of the user
                        if (task.isSuccessful) {
                            saveUserToFirestore(user.uid,name,email)
                            continuation.resume(AuthResult.Success(firebaseAuth.currentUser!!))
                        } else {
                            continuation.resume(AuthResult.Error(task.exception!!.message!!))
                        }
                    }
                } else {
                    continuation.resume(AuthResult.Error(task.exception!!.message!!))
                }
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    private fun saveUserToFirestore(uid: String, name: String, email: String){//might have to change it cause it is not MVVM
        val user = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to email
        )

        firestore.collection("users")
            .document(uid)
            .set(user,SetOptions.merge())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    println("User added to firestore")
                } else {
                    println("Failed to add user to firestore")
                }
            }
    }
}