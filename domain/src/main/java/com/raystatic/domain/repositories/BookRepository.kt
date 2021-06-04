package com.raystatic.domain.repositories

import com.raystatic.domain.common.Result
import com.raystatic.domain.entities.Volume
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getRemoteBooks(author:String):Result<List<Volume>>

    suspend fun getBookMarks():Flow<List<Volume>>

    suspend fun bookmark(book:Volume)

    suspend fun unBookmark(book: Volume)

}