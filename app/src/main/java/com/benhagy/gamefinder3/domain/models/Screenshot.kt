package com.benhagy.gamefinder3.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Screenshot(
    val id: Int,
    val image: String
): Parcelable
