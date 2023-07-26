package com.benhagy.gamefinder3.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

/*
because Room can't store non-primitive data types in a SQL database, this converter helps us store LDTs.

Room uses the converter to store the LDT as a string, then converts it back to an LDT when retrieved from the db
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