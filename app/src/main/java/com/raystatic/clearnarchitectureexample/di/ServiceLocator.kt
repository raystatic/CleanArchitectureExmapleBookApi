package com.raystatic.clearnarchitectureexample.di

import android.content.Context
import com.raystatic.data.api.NetworkModule
import com.raystatic.data.db.BooksDatabase
import com.raystatic.data.mappers.BookEntityMapper
import com.raystatic.data.mappers.BooksResponseMapper
import com.raystatic.data.repositories.BooksLocalDataSource
import com.raystatic.data.repositories.BooksLocalDataSourceImpl
import com.raystatic.data.repositories.BooksRemoteDataSourceImpl
import com.raystatic.data.repositories.BooksRepositoryImpl
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private var database:BooksDatabase?=null
    private val networkModule  by lazy {
        NetworkModule()
    }

    private val bookEntityMapper by lazy {
        BookEntityMapper()
    }

    @Volatile
    var booksRepository:BooksRepositoryImpl?=null

    fun provideBooksRepository(context: Context):BooksRepositoryImpl{
        synchronized(this){
            val b =  booksRepository ?: createBooksRepository(context)
            println("rahulray: $b")
            return b
        }
    }

    private fun createBooksRepository(context: Context):BooksRepositoryImpl{
        val newRepo =
            BooksRepositoryImpl(
                createLocalDataSource(context),
                BooksRemoteDataSourceImpl(
                    networkModule.createBooksApi("https://www.googleapis.com/"),
                    BooksResponseMapper()
                )
            )
        booksRepository = newRepo
        return newRepo
    }

    private fun createLocalDataSource(context: Context):BooksLocalDataSource{
        val database = database ?: createDatabase(context)
        return BooksLocalDataSourceImpl(
            bookDao = database.getBooksDao(),
            dispatcher = Dispatchers.IO,
            bookEntityMapper = bookEntityMapper
        )
    }

    private fun createDatabase(context: Context):BooksDatabase{
        val result = BooksDatabase.getDatabase(context)
        database = result
        return result
    }

}