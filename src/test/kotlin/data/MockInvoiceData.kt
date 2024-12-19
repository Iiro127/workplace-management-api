package data

import src.gen.java.org.openapitools.model.Invoice

class MockInvoiceData {
    companion object{

        /**
         * returns a mutableList of mock invoices
         *
         * @return MutableList<Invoice>
         */
        fun getinvoices(): MutableList<Invoice>{
            return mutableListOf(
                createMockInvoice(
                    id = "123123",
                    title = "Invoice title",
                    sum = 12.34,
                    userFirstName = "Jarmo",
                    dateAdded = "2024-19-12",
                    status = "Pending",
                    bankAccount = "1234567890"
                ),
                createMockInvoice(
                    id = "654654",
                    title = "Car repair",
                    sum = 234.00,
                    userFirstName = "Marko",
                    dateAdded = "2024-20-11",
                    status = "Paid",
                    bankAccount = "0987654321"
                )
            )
        }

        /**
         * A helper function to create a mock invoice
         *
         * @param id String
         * @param title String
         * @param sum Double
         * @param userFirstName String
         * @param dateAdded String
         * @param status String
         * @param bankAccount String
         *
         * @return Invoice
         */
        private fun createMockInvoice(
            id: String,
            title: String,
            sum: Double,
            userFirstName: String,
            dateAdded: String,
            status: String,
            bankAccount: String
        ): Invoice {
            val newInvoice = Invoice()

            newInvoice.id = id
            newInvoice.title = title
            newInvoice.sum = sum.toBigDecimal()
            newInvoice.userFirstName = userFirstName
            newInvoice.dateAdded = dateAdded
            newInvoice.status = status
            newInvoice.bankAccount = bankAccount

            return newInvoice
        }
    }
}