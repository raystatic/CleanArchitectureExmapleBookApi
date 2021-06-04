package com.raystatic.domain.usecases

import com.raystatic.domain.repositories.BookRepository

class GetBooksUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(author:String) = repository.getRemoteBooks(author)

}