package com.raystatic.data.db

import androidx.room.*
import com.raystatic.data.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book:BookEntity)

    @Query("SELECT * FROM books")
    fun getSavedBooks():Flow<List<BookEntity>>

    @Delete
    suspend fun deleteBook(book: BookEntity)

}