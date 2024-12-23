package com.example.controller

import com.example.db.InvoicesDatabaseController
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.model.Invoice

@ApplicationScoped
class InvoicesController {

    @Inject
    lateinit var databaseController: InvoicesDatabaseController

    /**
     * Gets all invoices
     *
     * @return MutableList<Invoice>
     */
    fun getInvoices(): MutableList<Invoice> {
        return databaseController.getInvoicesFromDatabase().toMutableList()
    }

    /**
     * Creates a new invoice
     *
     * @param invoice Invoice
     * @return Boolean
     */
    fun createInvoice(invoice: Invoice): Boolean {
        return databaseController.addInvoiceToDatabase(invoice)
    }

    fun findInvoice(invoiceId: String?): Invoice {
        return databaseController.findInvoiceFromDatabase(invoiceId)
    }

    fun deleteInvoice(invoiceId: String?): Boolean {
        return databaseController.deleteInvoiceFromDatabase(invoiceId)
    }
}