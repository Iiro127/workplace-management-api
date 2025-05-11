package com.example.db

import org.bson.Document
import src.gen.java.org.openapitools.model.Event
import src.gen.java.org.openapitools.model.Invoice
import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

abstract class DatabaseResource {
    /**
     * Maps user fetched from database
     *
     * @param doc Document
     * @return User
     */
    fun mapUser(doc: Document): User {
        return User()
            .id(doc.getString("id"))
            .firstName(doc.getString("firstName"))
            .lastName(doc.getString("lastName"))
            .isActive(doc.getBoolean("isActive"))
    }

    /**
     * Maps event fetched from database
     *
     * @param doc Document
     * @return Event
     */
    fun mapEvent(doc: Document): Event {
        return Event()
            .id(doc.getString("id"))
            .title(doc.getString("title"))
            .date(doc.getString("date"))
            .userid(doc.getString("userid"))
    }

    /**
     * Converts project to MongoDB document
     *
     * @param project Project
     * @return Document
     */
    fun projectToDocument(project: Project): Document {
        return Document()
            .append("id", project.id)
            .append("title", project.title)
            .append("dateAdded", project.dateAdded)
            .append("finishEstimate", project.finishEstimate)
            .append("customer", project.customer)
            .append("status", project.status)
            .append("members", project.members.map { user ->
                userToDocument(user)
            })
            .append("manager", project.manager?.let { userToDocument(it) })
    }

    /**
     * Converts invoice to MongoDB document
     *
     * @param invoice Invoice
     * @return Document
     */
    fun invoiceToDocument(invoice: Invoice): Document {
        return Document()
            .append("id", invoice.id)
            .append("title", invoice.title)
            .append("sum", invoice.sum)
            .append("userFirstName", invoice.userFirstName)
            .append("dateAdded", invoice.dateAdded)
            .append("status", invoice.status)
            .append("bankAccount", invoice.bankAccount)
    }

    /**
     * Converts user to MongoDB document
     *
     * @param user User
     * @return Document
     */
    fun userToDocument(user: User): Document {
        return Document()
            .append("id", user.id)
            .append("firstName", user.firstName)
            .append("lastName", user.lastName)
            .append("isActive", user.isActive)
    }

    /**
     * Converts event to MongoDB document
     *
     * @param event Event
     * @return Document
     */
    fun eventToDocument(event: Event): Document {
        return Document()
            .append("id", event.id)
            .append("title", event.title)
            .append("date", event.date)
            .append("userid", event.userid)
    }
}
