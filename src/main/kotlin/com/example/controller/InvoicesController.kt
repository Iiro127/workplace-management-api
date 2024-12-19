package com.example.controller

import com.example.db.InvoicesDatabaseController
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.model.Invoice

@ApplicationScoped
class InvoicesController {

    @Inject
    lateinit var databaseController: InvoicesDatabaseController

    fun getInvoices(): MutableList<Invoice> {
        return databaseController.getInvoicesFromDatabase().toMutableList()
    }
}