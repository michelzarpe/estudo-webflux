package br.com.michel.estudo_webflux.model

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "book")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Book(
    @Id
    @Column("id")
    var id: Long? = null,
    @Column("book_name")
    var bookName: String? = null,
    @Column("book_description")
    var bookDescription: String? = null
)