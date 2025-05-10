package br.com.michel.estudo_webflux.service

import br.com.michel.estudo_webflux.dto.BookCreateRequest
import br.com.michel.estudo_webflux.dto.BookUpdateRequest
import br.com.michel.estudo_webflux.model.Book
import br.com.michel.estudo_webflux.repository.BookRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class BookService (
    private val bookRepository: BookRepository
) {
    fun getById(id: Long): Mono<Book> =
        bookRepository.findById(id)
            .switchIfEmpty { Mono.error(IllegalArgumentException("Book not found")) }
            .onErrorMap { err -> IllegalArgumentException("Book not found") }

    fun addBook(bookCreateRequest: BookCreateRequest): Mono<Book> =
        bookRepository.save(BookCreateRequest.toBook(bookCreateRequest))

    fun getAll(): Mono<List<Book>> =
        bookRepository.findAll()
            .collectList()
            .switchIfEmpty { Mono.defer { Mono.just(emptyList()) } }
            .onErrorMap { err -> IllegalArgumentException("Book not found") }

    fun getBooks(): Flux<Book> = bookRepository.findAll()

  fun deleteById(id: Long): Mono<Void> =
      bookRepository.findById(id)
          .flatMap { book ->
              bookRepository.deleteById(id)
          }
          .switchIfEmpty { Mono.error(IllegalArgumentException("Book not found")) }
          .onErrorMap { it }

    fun updateBook(bookUpdateRequest: BookUpdateRequest, id: Long): Mono<Book> {
        return bookRepository.findById(id)
            .flatMap { book ->
                val updatedBook = BookUpdateRequest.bookUpdateRequest(bookUpdateRequest, book)
                bookRepository.save(updatedBook!!)
            }
            .switchIfEmpty { Mono.error(IllegalArgumentException("Book not found")) }
            .onErrorMap { it }
    }
}

