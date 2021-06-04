package com.raystatic.data.repositories

import com.raystatic.domain.entities.Volume
import kotlinx.coroutines.flow.Flow

interface BooksLocalDataSource {

    suspend fun bookmark(book:Volume)

    suspend fun unBookmark(book: Volume)

    suspend fun getBookmarks():Flow<List<Volume>>

}