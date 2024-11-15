package com.example

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.gradle.internal.impldep.com.google.common.collect.Range.greaterThan
import org.junit.jupiter.api.Test
import src.gen.java.org.openapitools.api.ProjectsApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll


class ProjectsTest {


    /**
     * Tests /projects -endpoint
     */
    @Test
    fun getAllProjects(){
        given()
            .`when`()
            .get("/projects")
            .then()
            .statusCode(200)
            .contentType("application/json")
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 8081
        }
    }
}