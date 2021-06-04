package com.raystatic.data.mappers

import com.raystatic.data.api.BooksResponse
import com.raystatic.domain.entities.Volume
import com.raystatic.domain.entities.VolumeInfo

class BooksResponseMapper {

    fun toVolumeList(response:BooksResponse):List<Volume>{
        return response.items.map {
            Volume(
                id = it.id,
                volumeInfo = VolumeInfo(
                    title = it.volumeInfo.title,
                    authors = it.volumeInfo.authors,
                    imageUrl = it.volumeInfo.imageLinks?.thumbnail?.replace("http","https")
                )
            )
        }
    }

}