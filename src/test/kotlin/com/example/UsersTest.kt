package com.example

import com.example.db.UsersDatabaseController
import data.MockUserData
import io.restassured.RestAssured
import org.bson.Document
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UsersTest: MockUserData() {

    @Test
    fun addAndFetchUserTest() {
        val result = databaseController.addUserToDatabase(getUser())
        val addedUser = databaseController.getDatabase()
            .find(Document("user.id", "654321"))
            .first()
            ?.get("user", Document::class.java)

        assertTrue(result)
        assertNotNull(addedUser)
        assertEquals("Markku", addedUser?.getString("firstName"))
        assertTrue(addedUser!!.getBoolean("isActive"))
    }

    @Test
    fun testUpdateUser() {
        val initialUser = getUser().id("123123")
        val updatedUser = initialUser.firstName("Updated")

        databaseController.addUserToDatabase(initialUser)
        val updateResult = databaseController.updateUserInDatabase("123123", updatedUser)

        assertTrue(updateResult)

        val updatedUserDocument = databaseController.getDatabase()
            .find(Document("user.id", "123123"))
            .first()
            ?.get("user", Document::class.java)

        assertNotNull(updatedUserDocument)
        assertEquals("Updated", updatedUserDocument?.getString("firstName"))
    }

    @Test
    fun testDeleteUser() {
        val deleteSuccess = databaseController.deleteUserInDatabase("123123")
        val deleteFail = databaseController.deleteUserInDatabase("zdkfhjkfgnj")

        assertTrue(deleteSuccess)
        assertFalse(deleteFail)
    }

    companion object {
        private lateinit var databaseController: UsersDatabaseController

        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 8081

            databaseController = UsersDatabaseController()
            databaseController.dbAddress = "mongodb://localhost:27017"
        }
    }
}