package br.com.michel.estudo_webflux.dto

import br.com.michel.estudo_webflux.model.Book

data class BookCreateRequest(
    val bookName: String,
    val bookDescription: String
){
    companion object {
        const val ZERO = 0
        const val THIRD_TWO = 32
        const val ONE_HUNDRED = 100

        fun toBook(bookCreateRequest: BookCreateRequest): Book {
            return Book(
                bookName = bookCreateRequest.bookName,
                bookDescription = bookCreateRequest.bookDescription
            )
        }
    }

    init {
        require(bookName.length in ZERO..THIRD_TWO && bookName.toDoubleOrNull() == null) { "bookName length must be between 0 and 32 or type invalid" }
        require(bookDescription.length in ZERO..ONE_HUNDRED && bookDescription.toDoubleOrNull() == null) { "bookDescription length must be between 0 and 100 or type invalid" }
    }
}
