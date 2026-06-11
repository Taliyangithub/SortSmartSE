package com.priya.sortsmartse.data

import androidx.room.*
import com.priya.sortsmartse.model.WasteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WasteItemDao {
    @Query("SELECT * FROM waste_items")
    fun getAllItems(): Flow<List<WasteItem>>

    @Query("SELECT * FROM waste_items WHERE id = :id")
    fun getItemById(id: String): Flow<WasteItem?>

    @Query("SELECT * FROM waste_items WHERE name LIKE '%' || :query || '%' OR keywords LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<WasteItem>>

    @Query("SELECT * FROM waste_items WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<WasteItem>>

    @Update
    fun updateItem(item: WasteItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<WasteItem>)

    @Query("SELECT COUNT(*) FROM waste_items")
    fun getCount(): Int
}
