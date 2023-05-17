package com.benhagy.gamefinder3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteGameEntity(
    @PrimaryKey val id: Int?,
    val name: String?,
    val released: String?,
    val backgroundImage: String?,
)
