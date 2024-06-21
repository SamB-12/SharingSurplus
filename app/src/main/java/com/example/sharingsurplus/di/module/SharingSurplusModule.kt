package com.example.sharingsurplus.di.module

import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.auth.AuthRepositoryImpl
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharingSurplusModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository {
        return impl
    }

    @Provides
    fun provideFirestoreRepository(impl: FirestoreRepositoryImpl): FirestoreRepository {
        return impl
    }

}