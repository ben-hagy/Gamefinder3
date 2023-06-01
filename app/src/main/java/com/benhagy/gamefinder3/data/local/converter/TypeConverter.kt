package com.benhagy.gamefinder3.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime


class TypeConverter {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

}