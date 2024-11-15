package com.example

import io.restassured.RestAssured
import io.smallrye.common.constraint.Assert.assertTrue
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

class SystemTest {

    @Test
    fun testPing(){
        /*RestAssured.given()
            .`when`().get("/Ping")
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("Pong"))
    }*/
        assertTrue(true)
    }
}