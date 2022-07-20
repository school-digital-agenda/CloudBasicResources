package br.com.sda.cloudbasic.sdagateway

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class SdaGatewayApplicationTests(
    private val applicationContext: ApplicationContext
) {

    @Test
    fun contextLoads() {
        assertNotNull(applicationContext)
    }
}
