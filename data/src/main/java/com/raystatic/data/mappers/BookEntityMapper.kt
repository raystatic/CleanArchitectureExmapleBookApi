package com.raystatic.data.mappers

import com.raystatic.data.entities.BookEntity
import com.raystatic.domain.entities.Volume
import com.raystatic.domain.entities.VolumeInfo

class BookEntityMapper {

    fun toBookEntity(volume: Volume):BookEntity{
        return BookEntity(
            id = volume.id,
            title = volume.volumeInfo.title,
            authors = volume.volumeInfo.authors,
            imageUrl = volume.volumeInfo.imageUrl
        )
    }

    fun toVolume(bookEntity: BookEntity):Volume{
        return Volume(
            id = bookEntity.id,
            volumeInfo = VolumeInfo(
                title = bookEntity.title,
                authors = bookEntity.authors,
                imageUrl = bookEntity.imageUrl
            )
        )
    }

}