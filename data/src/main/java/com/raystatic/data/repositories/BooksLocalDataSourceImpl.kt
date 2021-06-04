package com.raystatic.data.repositories

import com.raystatic.data.db.BookDao
import com.raystatic.data.mappers.BookEntityMapper
import com.raystatic.domain.entities.Volume
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BooksLocalDataSourceImpl(
    private val bookDao: BookDao,
    private val dispatcher:CoroutineDispatcher,
    private val bookEntityMapper: BookEntityMapper
):BooksLocalDataSource {

    override suspend fun bookmark(book: Volume) = withContext(dispatcher) {
        bookDao.saveBook(bookEntityMapper.toBookEntity(book))
    }

    override suspend fun unBookmark(book: Volume) = withContext(dispatcher) {
        bookDao.deleteBook(bookEntityMapper.toBookEntity(book))
    }

    override suspend fun getBookmarks(): Flow<List<Volume>> {
        val savedBooks = bookDao.getSavedBooks()
        return savedBooks.map {
            it.map {
                bookEntityMapper.toVolume(it)
            }
        }
    }
}