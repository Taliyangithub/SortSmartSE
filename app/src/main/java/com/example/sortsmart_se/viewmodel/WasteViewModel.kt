package com.example.sortsmart_se.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sortsmart_se.data.AppDatabase
import com.example.sortsmart_se.data.WasteRepository
import com.example.sortsmart_se.model.WasteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class WasteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WasteRepository

    val allItems: Flow<List<WasteItem>>
    val favoriteItems: Flow<List<WasteItem>>

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchResults: Flow<List<WasteItem>>

    init {
        val wasteItemDao = AppDatabase.getDatabase(application).wasteItemDao()
        repository = WasteRepository(wasteItemDao)
        
        allItems = repository.allItems
        favoriteItems = repository.favoriteItems

        searchResults = _searchQuery.flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allItems
            } else {
                repository.searchItems(query)
            }
        }

        // Initialize Database with Mock Data if empty
        viewModelScope.launch {
            repository.initializeDatabaseIfEmpty()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getItemById(id: String): Flow<WasteItem?> {
        return repository.getItemById(id)
    }

    fun toggleFavorite(item: WasteItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item)
        }
    }
}
