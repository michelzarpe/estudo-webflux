package br.com.michel.estudo_webflux.config

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLTransientConnectionException

@RestControllerAdvice
class Handler {
    @ExceptionHandler(
        DuplicateKeyException::class,
        IllegalArgumentException::class,
        HttpMessageNotReadableException::class,
        InvalidDefinitionException::class,
        HttpMessageConversionException::class,
        DuplicateKeyException::class,
        SQLTransientConnectionException::class
    )
    fun onBusinessException(e: Exception): ResponseEntity<*> {
        return ResponseEntity.unprocessableEntity().build<String>()
    }
}
