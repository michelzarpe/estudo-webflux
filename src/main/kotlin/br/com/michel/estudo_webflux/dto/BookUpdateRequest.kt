package br.com.michel.estudo_webflux.dto

import br.com.michel.estudo_webflux.model.Book

data class BookUpdateRequest(
    val bookName: String? = null,
    val bookDescription: String? = null
) {
    companion object {
        fun bookUpdateRequest(bookUpdateRequest: BookUpdateRequest?, book: Book?): Book? {
            if (bookUpdateRequest == null || book == null) return null
            if (!bookUpdateRequest.bookName.isNullOrBlank()) book.bookName = bookUpdateRequest.bookName
            if (!bookUpdateRequest.bookDescription.isNullOrBlank()) book.bookDescription = bookUpdateRequest.bookDescription
            return book
        }
    }
}