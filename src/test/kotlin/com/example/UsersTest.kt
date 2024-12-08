package com.example

import com.example.db.UsersDatabaseController
import data.MockUserData
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UsersTest: MockUserData() {

    @Test
    fun addUserTest() {
        val result = databaseController.addUserToDatabase(getUser())

        assertTrue(result)
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