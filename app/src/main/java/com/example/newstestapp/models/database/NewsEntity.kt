package com.example.newstestapp.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newstestapp.models.domain.NewsDTO
import kotlinx.datetime.Clock

@Entity(tableName = "FavoriteNewsTable")
data class NewsEntity(

    @PrimaryKey val id: Int,

    val author: String?,

    val title: String,

    val description: String,

    val url: String,

    val source: String,

    val image: String?,

    val category:String,

    val addDate: Long = Clock.System.now().epochSeconds
)

fun NewsEntity.toDTO(): NewsDTO {
    return NewsDTO(
        author = author,
        title = title,
        description = description,
        url = url,
        source = source,
        image = image,
        id = id,
        catgory = category
    )
}

fun NewsDTO.toEntity(): NewsEntity {
    return NewsEntity(
        author = author,
        title = title,
        description = description,
        url = url,
        source = source,
        image = image,
        id = id,
        category = catgory
    )
}

