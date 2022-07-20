package br.com.sda.cloudbasic.sdaconfigserver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.ApplicationContext

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Tag("Smoke")
class SdaConfigServerApplicationTests(
    private var applicationContext: ApplicationContext
) {

    @Test
    fun contextLoads() {
        assertThat(applicationContext)
            .isNotNull
    }
}
