package com.benhagy.gamefinder3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
entity representing primary database table for saving/deleting favorites.

represented as simple objects only presenting what will be seen on the individual list items, plus
a primary key, because the details screen is a shared destination for favorites and search results.
 */


@Entity(tableName = "bookmarks")
data class BookmarkedGameEntity(
    // initial implementation
    @PrimaryKey val id: Int?,
    val name: String?,
    val backgroundImage: String?,
    val released: String?,

    // additions <-- comment these out to run the app
    val developer: String?,
    val publisher: String?,
    val dateAdded: String?,
    val userNote: String,
    val userRating: Int?
)
