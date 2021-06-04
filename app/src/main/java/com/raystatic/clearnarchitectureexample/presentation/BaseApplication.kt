package com.raystatic.clearnarchitectureexample.presentation

import android.app.Application
import android.app.Service
import com.raystatic.clearnarchitectureexample.di.ServiceLocator
import com.raystatic.clearnarchitectureexample.mappers.BookWithStatusMapper
import com.raystatic.data.repositories.BooksRepositoryImpl
import com.raystatic.domain.usecases.BookmarkBookUseCase
import com.raystatic.domain.usecases.GetBookmarksUseCase
import com.raystatic.domain.usecases.GetBooksUseCase
import com.raystatic.domain.usecases.UnbookmarkUseCase
import dagger.hilt.android.HiltAndroidApp

class BaseApplication:Application(){

    private val bookRepository:BooksRepositoryImpl
        get() = ServiceLocator.provideBooksRepository(this)

    val getBooksUseCase:GetBooksUseCase
        get() = GetBooksUseCase(bookRepository)

    val getBookmarksUseCase: GetBookmarksUseCase
        get() = GetBookmarksUseCase(bookRepository)

    val bookmarkBooksUseCase: BookmarkBookUseCase
        get() = BookmarkBookUseCase(bookRepository)

    val unbookmarkBookUseCase: UnbookmarkUseCase
        get() = UnbookmarkUseCase(bookRepository)

    val bookWithStatusMapper = BookWithStatusMapper()

    override fun onCreate() {
        super.onCreate()

    }

}