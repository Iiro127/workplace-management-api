package com.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import data.MockProjectData
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.RestAssured.post
import jakarta.inject.Inject
import org.gradle.internal.impldep.com.google.common.collect.Range.greaterThan
import org.junit.jupiter.api.Test
import src.gen.java.org.openapitools.api.ProjectsApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll


class ProjectsTest: MockProjectData() {

    private lateinit var wireMockServer: WireMockServer

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

    @Test
    fun createProject(){
        val requestBody = ObjectMapper().writeValueAsString(getProject())

        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .`when`()
            .post("/projects")
            .then()
            .statusCode(200)
            .contentType("application/json")
    }

    companion object {
        private lateinit var wireMockServer: WireMockServer

        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 8081

            wireMockServer = WireMockServer(8081)
            wireMockServer.start()

            wireMockServer.stubFor(
                post(urlEqualTo("/projects"))
                    .willReturn(
                        aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            ObjectMapper().writeValueAsString(getProjects())
                        )
                    )
            )
            wireMockServer.stubFor(
                get(urlEqualTo("/projects"))
                    .willReturn(jsonResponse(ObjectMapper().writeValueAsString(getProjects()), 200))
            )
        }
    }
}