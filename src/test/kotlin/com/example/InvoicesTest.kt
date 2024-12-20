package com.example

import com.example.db.InvoicesDatabaseController
import data.MockInvoiceData
import io.restassured.RestAssured
import org.bson.Document
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class InvoicesTest: MockInvoiceData() {

    @Test
    fun testAddAndFetchInvoice(){
        val result = databaseController.addInvoiceToDatabase(getInvoice())
        val addedInvoice = databaseController.getDatabase()
            .find(Document("invoice.id", "abcdef"))
            .first()
            ?.get("invoice", Document::class.java)

        Assertions.assertTrue(result)
        Assertions.assertNotNull(addedInvoice)
        Assertions.assertEquals("Title123", addedInvoice?.getString("title"))
        Assertions.assertEquals("Paid", addedInvoice?.getString("status"))
    }

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