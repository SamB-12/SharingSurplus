package com.example.sharingsurplus.di.module

import com.example.sharingsurplus.data.repository.auth.AuthRepository
import com.example.sharingsurplus.data.repository.auth.AuthRepositoryImpl
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepository
import com.example.sharingsurplus.data.repository.firestore.FirestoreRepositoryImpl
import com.example.sharingsurplus.data.repository.storage.FirebaseStorageRepository
import com.example.sharingsurplus.data.repository.storage.FirebaseStorageRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This is a Dagger module that provides the necessary dependencies for the different repositories in the app.
 */
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
    @Singleton
    fun provideStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository {
        return impl
    }

    @Provides
    fun provideFirestoreRepository(impl: FirestoreRepositoryImpl): FirestoreRepository {
        return impl
    }

    @Provides
    fun provideFirebaseStorageRepository(impl: FirebaseStorageRepositoryImpl) : FirebaseStorageRepository{
        return impl
    }


}