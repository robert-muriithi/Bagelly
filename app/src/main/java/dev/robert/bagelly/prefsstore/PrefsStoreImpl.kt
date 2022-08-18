package dev.robert.bagelly.prefsstore

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.migrations.SharedPreferencesMigration
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val STORE_NAME = "bagelly_data_store"

class PrefsStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : PrefsStore {


    override fun isNightMode(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleNightMode() {
        TODO("Not yet implemented")
    }
}