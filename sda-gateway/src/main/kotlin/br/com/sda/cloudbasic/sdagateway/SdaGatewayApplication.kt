package br.com.sda.cloudbasic.sdagateway

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class SdaGatewayApplication {
    @Value("\${user.role}")
    private val role: String? = null

    @GetMapping(produces = [MediaType.TEXT_PLAIN_VALUE])
    fun whoami(): String? = role
}

fun main(args: Array<String>) {
    runApplication<SdaGatewayApplication>(*args)
}
