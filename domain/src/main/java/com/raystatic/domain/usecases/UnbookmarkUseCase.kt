package com.raystatic.domain.usecases

import com.raystatic.domain.entities.Volume
import com.raystatic.domain.repositories.BookRepository

class UnbookmarkUseCase(
    private val booksRepository: BookRepository
) {

    suspend operator fun invoke(book:Volume) = booksRepository.unBookmark(book)

}