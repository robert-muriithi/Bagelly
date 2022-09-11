package dev.robert.bagelly.ui.fragments.search.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class SearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
    }


}