package com.benhagy.gamefinder3.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/* primary model for Screenshot objects
response objects are mapped to this model
model is used in the repo to map screenshots (a different api call) to the overall Game Details
parcelable because android doesn't support complex data types in the bundle
 */

@Parcelize
data class Screenshot(
    val id: Int,
    val image: String
): Parcelable
