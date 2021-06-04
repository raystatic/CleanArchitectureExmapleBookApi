package com.raystatic.domain.usecases

import com.raystatic.domain.repositories.BookRepository

class GetBookmarksUseCase(
    private val booksRepository: BookRepository
) {

    suspend operator fun invoke() = booksRepository.getBookMarks()

}