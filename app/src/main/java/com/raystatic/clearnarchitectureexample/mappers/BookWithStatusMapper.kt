package com.raystatic.clearnarchitectureexample.mappers

import com.raystatic.clearnarchitectureexample.entities.BookWithStatus
import com.raystatic.clearnarchitectureexample.entities.BookmarkStatus
import com.raystatic.domain.entities.Volume
import com.raystatic.domain.entities.VolumeInfo

class BookWithStatusMapper {

    fun fromVolumeToBookWithStatus(
        volumes:List<Volume>,
        bookmarks:List<Volume>
    ):List<BookWithStatus>{
        val commonElements = volumes.filter { it.id in bookmarks.map { it.id } }
        val booksWithStatus = arrayListOf<BookWithStatus>()
        for (volume in volumes){
            if(volume in commonElements){
                booksWithStatus.add(
                    BookWithStatus(
                        id = volume.id,
                        title = volume.volumeInfo.title,
                        authors = volume.volumeInfo.authors,
                        imageUrl = volume.volumeInfo.imageUrl,
                        status = BookmarkStatus.BOOKMARKED
                    )
                )
            }else{
                booksWithStatus.add(
                    BookWithStatus(
                        id = volume.id,
                        title = volume.volumeInfo.title,
                        authors = volume.volumeInfo.authors,
                        imageUrl = volume.volumeInfo.imageUrl,
                        status = BookmarkStatus.UNBOOKMARKED
                    )
                )
            }
        }
        return booksWithStatus.sortedBy { it.id }
    }

    fun fromBookWithStatusToVolume(bookWithStatus: BookWithStatus):Volume{
        return Volume(
            id = bookWithStatus.id,
            VolumeInfo(
                title = bookWithStatus.title,
                authors = bookWithStatus.authors,
                imageUrl = bookWithStatus.imageUrl
            )
        )
    }

}