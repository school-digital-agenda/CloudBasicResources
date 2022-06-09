package br.com.sda.cloudbasic.sdaconfigserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class SdaConfigServerApplication

fun main(args: Array<String>) {
    runApplication<SdaConfigServerApplication>(*args)
}
