package com.example.api

import jakarta.enterprise.context.RequestScoped
import src.gen.java.org.openapitools.api.InvoicesApi
import src.gen.java.org.openapitools.model.Invoice

@RequestScoped
class InvoicesApi: InvoicesApi {
    override fun getInvoices(): MutableList<Invoice> {
        TODO("Not yet implemented")
    }
}