package com.example.db

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import src.gen.java.org.openapitools.model.Invoice
import src.gen.java.org.openapitools.model.Project

@ApplicationScoped
class InvoicesDatabaseController: DatabaseResource() {

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

    /**
     * Gets all invoices from database
     *
     * @return List<Invoice>
     */
    fun getInvoicesFromDatabase(): List<Invoice> {
        val collection = getDatabase().find()

        return collection.map { doc ->
            val invoiceDoc = doc.get("invoice", Document::class.java)

            Invoice()
                .id(invoiceDoc.getString("id"))
                .title(invoiceDoc.getString("title"))
                .sum(invoiceDoc.getDouble("sum").toBigDecimal())
                .userFirstName(invoiceDoc.getString("userFirstName"))
                .dateAdded(invoiceDoc.getString("dateAdded"))
                .status(invoiceDoc.getString("status"))
                .bankAccount(invoiceDoc.getString("bankAccount"))
        }.toList()
    }

    /**
     * Adds an invoice to database
     *
     * @param invoice Invoice
     * @return Boolean
     */
    fun addInvoiceToDatabase(invoice: Invoice): Boolean {
        return try {
            val invoiceDocument = Document()
                .append("invoice", invoiceToDocument(invoice))
            getDatabase().insertOne(invoiceDocument)

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Finds an invoice from database with id
     *
     * @param invoiceId String
     * @return Invoice
     */
    fun findInvoiceFromDatabase(invoiceId: String?): Invoice {
        return getDatabase()
            .find(Document("invoice.id", invoiceId))
            .firstOrNull()
            ?.get("invoice", Document::class.java)
            ?.let { invoiceDoc ->
                Invoice()
                    .id(invoiceDoc.getString("id"))
                    .title(invoiceDoc.getString("title"))
                    .sum(invoiceDoc.getDouble("sum").toBigDecimal())
                    .userFirstName(invoiceDoc.getString("userFirstName"))
                    .status(invoiceDoc.getString("status"))
                    .dateAdded(invoiceDoc.getString("dateAdded"))
                    .bankAccount(invoiceDoc.getString("bankAccount"))
            }!!
    }

    fun deleteInvoiceFromDatabase(invoiceId: String?): Boolean {
        val result = getDatabase().deleteMany(Document("invoice.id", invoiceId))

        return result.deletedCount > 0
    }
}
