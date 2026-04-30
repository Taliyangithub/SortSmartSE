package com.example.sortsmart_se.data

import androidx.room.TypeConverter
import com.example.sortsmart_se.model.RecycleAction
import com.example.sortsmart_se.model.WasteCategory

class Converters {
    @TypeConverter
    fun fromWasteCategory(value: WasteCategory): String {
        return value.name
    }

    @TypeConverter
    fun toWasteCategory(value: String): WasteCategory {
        return WasteCategory.valueOf(value)
    }

    @TypeConverter
    fun fromRecycleAction(value: RecycleAction): String {
        return value.name
    }

    @TypeConverter
    fun toRecycleAction(value: String): RecycleAction {
        return RecycleAction.valueOf(value)
    }
}
