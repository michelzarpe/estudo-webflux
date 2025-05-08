package br.com.michel.estudo_webflux.controller

import br.com.michel.estudo_webflux.dto.BookCreateRequest
import br.com.michel.estudo_webflux.dto.BookUpdateRequest
import br.com.michel.estudo_webflux.model.Book
import br.com.michel.estudo_webflux.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/books")
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Long): Mono<ResponseEntity<Book>> =
        bookService.getById(id).map { ResponseEntity.ok(it) }.defaultIfEmpty(ResponseEntity.notFound().build())

    @PostMapping
    fun addBook(@RequestBody bookCreateRequest: BookCreateRequest): Mono<ResponseEntity<Book>> =
        bookService.addBook(bookCreateRequest).map { ResponseEntity.ok(it) }

    @PutMapping("/{id}")
    fun updateBook(
        @RequestBody bookUpdateRequest: BookUpdateRequest, @PathVariable id: Long
    ): Mono<ResponseEntity<Book>> = bookService.updateBook(bookUpdateRequest, id).map { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): Mono<ResponseEntity<Void>> =
        bookService.deleteById(id).map { ResponseEntity.noContent().build() }


    @GetMapping
    fun getBooks(): Mono<ResponseEntity<Flux<Book>>> = Mono.just(ResponseEntity.ok(bookService.getBooks()))
}

//https://medium.com/@dibyajyotidhar/reactive-springboot-with-r2dbc-postgres-connection-along-with-integration-tests-using-06eef7cd9938


