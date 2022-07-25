package dev.robert.bagelly.ui.fragments.account.viewmodel

import androidx.lifecycle.ViewModel
import dev.robert.bagelly.data.repository.MainRepository
import javax.inject.Inject

class MyAccountViewModel
    @Inject constructor(
        private val repository: MainRepository
    ): ViewModel() {

}