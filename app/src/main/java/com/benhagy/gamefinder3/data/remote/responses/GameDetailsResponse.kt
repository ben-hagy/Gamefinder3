package com.benhagy.gamefinder3.data.remote.responses

import com.benhagy.gamefinder3.domain.models.GameDetails
import com.google.gson.annotations.SerializedName

data class GameDetailsResponse(
    val id: Int?,
    val name: String?,
    val description: String?,
    val metacritic: Int?,
    val released: String?,
    @SerializedName("background_image") val backgroundImage: String?,
    val website: String?,
    val playtime: Int?,
    val platforms: List<PlatformResponse>,
    val developers: List<DeveloperResponse>,
    val publishers: List<PublisherResponse>,
    val genres: List<GenreResponse>,
    val tags: List<TagResponse>,
    @SerializedName("esrb_rating")
    val esrb: EsrbResponse?,
) {
    fun toGameDetails(): GameDetails {
        return GameDetails(
            id = id,
            name = name,
            description = description,
            metacritic = metacritic,
            released = released,
            backgroundImage = backgroundImage,
            website = website,
            playtime = playtime,
            platforms = platforms.map { it.platform.toPlatform() },
            developers = developers.map { it.toDeveloper() },
            publishers = publishers.map { it.toPublisher() },
            genres = genres.map { it.toGenre() },
            tags = tags.map { it.toTag() },
            esrb = esrb?.toEsrb()
        )
    }
}
