package com.example.api

import com.example.controller.InvoicesController
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.api.InvoicesApi
import src.gen.java.org.openapitools.model.Invoice

@RequestScoped
class InvoicesApi: InvoicesApi {

    @Inject
    lateinit var invoicesController: InvoicesController
    override fun createInvoice(invoice: Invoice?): Invoice {
        return try {
            if (invoicesController.createInvoice(invoice!!)){
                invoice
            } else {
                Invoice()
            }
        } catch (e: Exception){
            throw e
        }
    }

    override fun deleteInvoice(invoiceId: String?): Boolean {
        return try {
            invoicesController.deleteInvoice(invoiceId)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun findInvoice(invoiceId: String?): Invoice {
        return try {
            invoicesController.findInvoice(invoiceId)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getInvoices(): MutableList<Invoice> {
        return try {
            invoicesController.getInvoices()
        } catch (e: Exception){
            throw e
        }
    }
}