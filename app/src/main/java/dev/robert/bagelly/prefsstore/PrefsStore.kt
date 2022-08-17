package dev.robert.bagelly.prefsstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun isNightMode() : Flow<Boolean>

    suspend fun toggleNightMode()

}