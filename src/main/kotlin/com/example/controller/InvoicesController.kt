package com.example.controller

import com.example.db.InvoicesDatabaseController
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class InvoicesController {

    @Inject
    lateinit var invoicesDatabaseController: InvoicesDatabaseController
}