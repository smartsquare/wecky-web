package de.smartsquare.wecky.weckyweb

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.wecky.weckyweb.domain.Website
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class JacksonTest {

    val mapper = jacksonObjectMapper()

    @Test
    fun test_jackson_serialization() {
        val jsonString = mapper.writeValueAsString(Website("foobar", "www.foobar.com", "foo@bar.de"))
        assertNotNull(mapper.readValue(jsonString, Website::class.java))
    }
}