package com.example.sharingsurplus.data.repository.auth

import com.example.sharingsurplus.data.model.User
import com.example.sharingsurplus.data.repository.AuthResult
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, //it gets injected by dagger which finds def from module
    private val firestoreRepositoryImpl: FirestoreRepository
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
                            CoroutineScope(Dispatchers.IO).launch {
                                saveUserToFirestore(user.uid,name,email)
                                continuation.resume(AuthResult.Success(firebaseAuth.currentUser!!))
                            }
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

    suspend fun saveUserToFirestore(uid: String, name: String, email: String): Boolean{//might have to change it cause it is not MVVM
        val user = User(uid = uid,name = name,email = email, createdAt = System.currentTimeMillis())
        firestoreRepositoryImpl.createUser(user)
        return true
    }
}