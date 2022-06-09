package br.com.sda.cloudbasic.SdaConfigServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SdaConfigServerApplication

fun main(args: Array<String>) {
	runApplication<SdaConfigServerApplication>(*args)
}
