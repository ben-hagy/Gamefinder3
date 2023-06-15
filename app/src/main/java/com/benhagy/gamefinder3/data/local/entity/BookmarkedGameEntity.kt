package com.benhagy.gamefinder3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/*
entity representing primary database table for saving/deleting favorites.

local-datetime is taken the instant an item is added to the db, to help logic for sorting the display
order of the favorites list
 */


@Entity(tableName = "bookmarks")
data class BookmarkedGameEntity(
    @PrimaryKey val id: Int?,
    val name: String?,
    val backgroundImage: String?,
    val dateAdded: LocalDateTime = LocalDateTime.now(),
    val userNote: String,
    val userRating: Float?
)