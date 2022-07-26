package dev.robert.bagelly.ui.fragments.notifications.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.bagelly.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel
    @Inject constructor(
        private val repository : MainRepository
    ): ViewModel() {
}