package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Esrb

// response object and mapper for Esrb Ratings info

data class EsrbResponse(
    val id: Int?,
    val name: String?,
) {
    fun toEsrb(): Esrb {
        return Esrb(id = id, name = name)
    }
}