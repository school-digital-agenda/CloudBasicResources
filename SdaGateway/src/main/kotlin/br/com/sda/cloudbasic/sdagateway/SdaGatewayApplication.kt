package br.com.sda.cloudbasic.sdagateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SdaGatewayApplication

fun main(args: Array<String>) {
	runApplication<SdaGatewayApplication>(*args)
}
