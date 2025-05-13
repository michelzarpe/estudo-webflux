package br.com.michel.estudo_webflux

import br.com.michel.estudo_webflux.dto.BookCreateRequest
import br.com.michel.estudo_webflux.dto.BookUpdateRequest
import br.com.michel.estudo_webflux.model.Book
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostgresTestcontainerApplicationTests @Autowired constructor(
    val webTestClient: WebTestClient
) {

    @Order(1)
    @Test
    fun contextoLoads() {
    }

    @Order(2)
    @Test
    fun testCreateRequest() {
        webTestClient
            .post()
            .uri("/v1/book")
            .bodyValue(createRequest())
            .exchange()
            .expectStatus().isOk
            .expectBody().json(createdBook())
    }

    @Order(3)
    @Test
    fun testGetRequestOnCreation() {
        webTestClient
            .get()
            .uri("/v1/book/1")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(createdBook())
    }

    @Order(4)
    @Test
    fun testUpdateRequest() {
        webTestClient
            .put()
            .uri("/v1/book/1")
            .bodyValue(updateBookRequest())
            .exchange()
            .expectStatus().isOk
            .expectBody().json(updatedBook())
    }

    @Order(5)
    @Test
    fun testGetRequestOnUpdate() {
        webTestClient
            .get()
            .uri("/v1/book/1")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(updatedBook())
    }

    @Order(6)
    @Test
    fun testDeleteRequest() {
        webTestClient
            .delete()
            .uri("/v1/book/1")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(updatedBook())
    }

    @Order(7)
    @Test
    fun testGetRequestOnDelete() {
        webTestClient
            .get()
            .uri("/v1/book/1")
            .exchange()
            .expectStatus().isOk
            .expectBody().json("{}")
    }

    @Order(8)
    @Test
    fun testGetAllBooks() {
        webTestClient
            .post()
            .uri("/v1/book")
            .bodyValue(createRequest2())
            .exchange()
            .expectStatus().isOk
        webTestClient
            .post()
            .uri("/v1/book")
            .bodyValue(createRequest3())
            .exchange()
            .expectStatus().isOk
        webTestClient
            .post()
            .uri("/v1/book")
            .bodyValue(createRequest4())
            .exchange()
            .expectStatus().isOk

        webTestClient
            .get()
            .uri("/v1/books")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(createdBooks())
    }

    private fun updateBookRequest() = BookUpdateRequest(bookName = "book1_modified", bookDescription = "desc1_modified")
    private fun createRequest() = BookCreateRequest(bookName = "book1", bookDescription = "desc1")
    private fun createRequest2() = BookCreateRequest(bookName = "book2", bookDescription = "desc2")
    private fun createRequest3() = BookCreateRequest(bookName = "book3", bookDescription = "desc3")
    private fun createRequest4() = BookCreateRequest(bookName = "book4", bookDescription = "desc4")

    private fun updatedBook(): String {
        val book = Book().apply {
            id = 1L
            bookName = "book1_modified"
            bookDescription = "desc1_modified"
        }
        return objectToJsonString(book)
    }

    private fun createdBook(): String {
        val book = Book().apply {
            id = 1L
            bookName = "book1"
            bookDescription = "desc1"
        }
        return objectToJsonString(book)
    }

    private fun createdBooks(): String {
        val book2 = Book().apply {
            id = 2L
            bookName = "book2"
            bookDescription = "desc2"
        }
        val book3 = Book().apply {
            id = 3L
            bookName = "book3"
            bookDescription = "desc3"
        }
        val book4 = Book().apply {
            id = 4L
            bookName = "book4"
            bookDescription = "desc4"
        }
        return objectToJsonString(listOf(book2, book3, book4))
    }

    private fun objectToJsonString(obj: Any): String =
        try {
            ObjectMapper().writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
}