package com.priya.sortsmartse.data

import androidx.room.TypeConverter
import com.priya.sortsmartse.model.RecycleAction
import com.priya.sortsmartse.model.WasteCategory

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
