package br.com.michel.estudo_webflux.repository

import br.com.michel.estudo_webflux.model.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
interface BookRepository: ReactiveCrudRepository<Book, Long> {
    fun findByBookName(author: String): Flux<Book>
}