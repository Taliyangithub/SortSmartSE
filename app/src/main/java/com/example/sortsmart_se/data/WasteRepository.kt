package com.example.sortsmart_se.data

import com.example.sortsmart_se.model.WasteItem
import com.example.sortsmart_se.model.mockWasteItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WasteRepository(private val wasteItemDao: WasteItemDao) {

    val allItems: Flow<List<WasteItem>> = wasteItemDao.getAllItems()
    val favoriteItems: Flow<List<WasteItem>> = wasteItemDao.getFavorites()

    fun getItemById(id: String): Flow<WasteItem?> {
        return wasteItemDao.getItemById(id)
    }

    fun searchItems(query: String): Flow<List<WasteItem>> {
        return wasteItemDao.searchItems(query)
    }

    suspend fun toggleFavorite(item: WasteItem) {
        withContext(Dispatchers.IO) {
            val updatedItem = item.copy(isFavorite = !item.isFavorite)
            wasteItemDao.updateItem(updatedItem)
        }
    }

    suspend fun initializeDatabaseIfEmpty() {
        withContext(Dispatchers.IO) {
            if (wasteItemDao.getCount() == 0) {
                wasteItemDao.insertAll(mockWasteItems)
            }
        }
    }
}
