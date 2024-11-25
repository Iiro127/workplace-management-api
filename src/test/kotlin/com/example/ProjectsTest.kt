package com.example

import com.example.db.ProjectsDatabaseController
import data.MockProjectData
import io.restassured.RestAssured
import org.bson.Document
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ProjectsTest: MockProjectData() {

    @Test
    fun testAddAndGetProject() {
        val result = databaseController.addProjectToDatabase(getProject())

        assertTrue(result)

        val addedProject = databaseController.getDatabase()
            .find(Document("project.id", "654321"))
            .first()
            ?.get("project", Document::class.java)

        assertNotNull(addedProject)
        assertEquals("Test2", addedProject?.getString("title"))
        assertEquals("Planning", addedProject?.getString("status"))
    }

    @Test
    fun testUpdateProject() {
        val initialProject = getProject().id("123123")
        val updatedProject = initialProject.title("Updated")

        databaseController.addProjectToDatabase(initialProject)
        val updateResult = databaseController.updateProjectInDatabase("123123", updatedProject)

        assertTrue(updateResult)

        val updatedProjectDocument = databaseController.getDatabase()
            .find(Document("project.id", "123123"))
            .first()
            ?.get("project", Document::class.java)

        assertNotNull(updatedProjectDocument)
        assertEquals("Updated", updatedProjectDocument?.getString("title"))
    }


    companion object {
        private lateinit var databaseController: ProjectsDatabaseController

        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 8081

            databaseController = ProjectsDatabaseController()
            databaseController.dbAddress = "mongodb://localhost:27017"
        }
    }
}