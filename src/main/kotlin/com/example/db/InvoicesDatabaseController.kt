package com.example.db

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import src.gen.java.org.openapitools.model.Invoice

@ApplicationScoped
class InvoicesDatabaseController {

    @ConfigProperty(name = "database.access.endpoint")
    lateinit var dbAddress: String

    /**
     * Gets database for projects
     *
     * @return MongoCollection<Document>
     */
    fun getDatabase(): MongoCollection<Document> {
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace_management-db")
        return database.getCollection("invoices")
    }
    fun getInvoicesFromDatabase(): List<Invoice> {
        val collection = getDatabase().find()

        return collection.map { doc ->
            val invoiceDoc = doc.get("invoice", Document::class.java)

            Invoice()
                .id(invoiceDoc.getString("id"))
                .title(invoiceDoc.getString("title"))
                .sum(invoiceDoc.getString("sum").toBigDecimal())
                .userFirstName(invoiceDoc.getString("userFirstName"))
                .dateAdded(invoiceDoc.getString("dateAdded"))
                .status(invoiceDoc.getString("status"))
                .bankAccount(invoiceDoc.getString("bankAccount"))
        }.toList()
    }

}
