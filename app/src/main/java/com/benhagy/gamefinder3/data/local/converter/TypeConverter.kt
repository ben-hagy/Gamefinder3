package com.benhagy.gamefinder3.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

/*
converter handles database storage of local dates, since Room can't store non-primitives

this converter stores the LDT as a string, then converts it back to an LDT when retrieved from the db
 */

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