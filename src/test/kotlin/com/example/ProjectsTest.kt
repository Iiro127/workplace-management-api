package com.example

import com.github.tomakehurst.wiremock.WireMockServer
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import data.MockProjectData
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.bson.Document
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectsTest: MockProjectData() {

    /**
     * Tests getting all projects
     */
    @Test
    fun getAllProjects(){
        val title = collection
            .find()
            .first()
            ?.get("project", Document::class.java)
            ?.getString("title")

        assertEquals("Test1", title)
    }

    /**
     * Tests project creation
     */
    @Test
    fun createProject(){
        val projectDocument = Document()
            .append("project", getProject())
        val result = collection.insertOne(projectDocument)

        assertTrue(result.wasAcknowledged())
    }

    /**
     * Tests finding project
     */
    @Test
    fun findProjectTest() {
        collection.deleteMany(Document())
        collection.insertMany(getProjects())

        val projectTitle = collection
            .find(Document("project.id", "123456"))
            .first()
            ?.get("project", Document::class.java)
            ?.getString("title")

        assertEquals("Test1", projectTitle)
    }

    /**
     * Tests project updating
     */
    @Test
    fun testUpdateProjectInfo() {
        collection.updateOne(
            Document("project.id", "123456"),
            Document("\$set", Document("project.title", "Edited"))
        )

        val updatedProject = collection
            .find(Document("project.id", "123456"))
            .first()

        val updatedTitle = updatedProject
            ?.get("project", Document::class.java)
            ?.getString("title")

        assertEquals("Edited", updatedTitle)
    }

    companion object {
        private lateinit var mongoClient: MongoClient
        private lateinit var database: MongoDatabase
        private lateinit var collection: MongoCollection<Document>

        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 8081

            mongoClient = MongoClients.create("mongodb://localhost:27017")
            database = mongoClient.getDatabase("testdb")
            collection = database.getCollection("projects")
            collection.deleteMany(Document())
            collection.insertMany(getProjects())
        }

        @JvmStatic
        @AfterAll
        fun cleanup(): Unit {
            collection.deleteMany(Document())
        }
    }
}