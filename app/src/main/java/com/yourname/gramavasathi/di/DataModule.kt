package com.yourname.gramavasathi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.yourname.gramavasathi.data.repository.AuthRepository
import com.yourname.gramavasathi.data.repository.BookingRepository
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.yourname.gramavasathi.data.repository.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage =
        FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepository(auth, firestore)

    @Provides
    @Singleton
    fun provideListingRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): ListingRepository = ListingRepository(firestore)

    @Provides
    @Singleton
    fun provideReviewRepository(
        firestore: FirebaseFirestore
    ): ReviewRepository = ReviewRepository(firestore)

    @Provides
    @Singleton
    fun provideBookingRepository(
        firestore: FirebaseFirestore
    ): BookingRepository = BookingRepository(firestore)
}