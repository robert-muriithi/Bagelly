package dev.robert.bagelly.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.robert.bagelly.data.repository.AuthenticationRepository
import dev.robert.bagelly.data.repository.AuthenticationRepositoryImpl
import dev.robert.bagelly.data.repository.MainRepository
import dev.robert.bagelly.data.repository.MainRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference,
        app: Application,
    ): MainRepository {
        return MainRepositoryImpl(
            database,
            storageReference,
            app
        )
    }

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providerAuthRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore,
        app: Application,
        sharedPreferences: SharedPreferences
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth, db, app, sharedPreferences)
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(@ApplicationContext app: Application): Application = app

    @Provides
    @Singleton
    fun provideStorageReference(): StorageReference =
        FirebaseStorage.getInstance().reference

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences("bagelly", Context.MODE_PRIVATE)

}