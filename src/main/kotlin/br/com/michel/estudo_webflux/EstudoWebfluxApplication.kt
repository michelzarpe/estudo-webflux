package br.com.michel.estudo_webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EstudoWebfluxApplication

fun main(args: Array<String>) {
	runApplication<EstudoWebfluxApplication>(*args)
}
