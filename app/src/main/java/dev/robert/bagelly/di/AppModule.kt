package dev.robert.bagelly.di

import android.app.Application
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
        collectionReference: CollectionReference
    ): MainRepository {
        return MainRepositoryImpl(
            database,
            storageReference,
            collectionReference
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
        app: Application
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth, db, app)
    }

    @Provides
    @Singleton
    fun provideCollectionReference(db: FirebaseFirestore): CollectionReference {
        return db.collection("images")
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(@ApplicationContext app: Application): Application = app

    @Provides
    @Singleton
    fun provideStorageReference(): StorageReference =
        FirebaseStorage.getInstance().reference

}