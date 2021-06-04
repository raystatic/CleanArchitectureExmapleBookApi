package com.raystatic.data.repositories

import com.raystatic.domain.common.Result
import com.raystatic.domain.entities.Volume
import com.raystatic.domain.repositories.BookRepository
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImpl(
    private val localDataSource:BooksLocalDataSource,
    private val remoteDataSource: BookRemoteDataSource
):BookRepository {

    override suspend fun getRemoteBooks(author: String): Result<List<Volume>> {
        println("rahulray")
        return remoteDataSource.getBooks(author)
    }

    override suspend fun getBookMarks(): Flow<List<Volume>> {
        return localDataSource.getBookmarks()
    }

    override suspend fun bookmark(book: Volume) {
        localDataSource.bookmark(book)
    }

    override suspend fun unBookmark(book: Volume) {
        localDataSource.unBookmark(book)
    }
}