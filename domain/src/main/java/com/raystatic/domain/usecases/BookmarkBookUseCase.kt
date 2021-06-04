package com.raystatic.domain.usecases

import com.raystatic.domain.entities.Volume
import com.raystatic.domain.repositories.BookRepository

class BookmarkBookUseCase(
    private val booksRepository: BookRepository
) {

    suspend operator fun invoke(book:Volume) = booksRepository.bookmark(book)
}