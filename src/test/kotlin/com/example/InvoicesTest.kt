package com.example

import com.example.db.InvoicesDatabaseController
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class InvoicesTest {

    companion object {
        private lateinit var databaseController: InvoicesDatabaseController

        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 8081

            databaseController = InvoicesDatabaseController()
            databaseController.dbAddress = "mongodb://localhost:27017"
        }
    }
}