package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.Esrb


data class EsrbResponse(
    val id: Int?,
    val name: String?,
) {
    fun toEsrb(): Esrb {
        return Esrb(id = id, name = name)
    }
}