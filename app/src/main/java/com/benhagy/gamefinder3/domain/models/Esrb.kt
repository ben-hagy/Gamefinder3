package com.benhagy.gamefinder3.domain.models

// primary model for Esrb rating objects
// response objects are mapped to this model for use in our screens

data class Esrb(
    val id: Int?,
    val name: String?,
)