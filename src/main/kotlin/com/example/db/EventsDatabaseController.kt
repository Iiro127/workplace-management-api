package com.example.db

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import src.gen.java.org.openapitools.model.Event
import src.gen.java.org.openapitools.model.Project

@ApplicationScoped
class EventsDatabaseController: DatabaseResource() {
    @ConfigProperty(name = "database.access.endpoint")
    lateinit var dbAddress: String

    @Inject
    lateinit var jwt: JsonWebToken

    /**
     * Gets database for projects
     *
     * @return MongoCollection<Document>
     */
    fun getDatabase(): MongoCollection<Document> {
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace_management-db")
        return database.getCollection("events")
    }

    /**
     * Adds a new event to database
     *
     * @param event Event
     * @return Boolean
     */
    fun addEventToDatabase(event: Event?): Boolean {
        return try {
            val eventDocument = Document()
                .append("event", eventToDocument(event!!))
            getDatabase().insertOne(eventDocument)

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Deletes an event from database
     *
     * @param eventid String
     * @return Boolean
     */
    fun deleteEventFromDatabase(eventid: String): Boolean {
        val result = getDatabase().deleteMany(Document("event.id", eventid))

        return result.deletedCount > 0
    }

    /**
     * Gets all event from database
     *
     * @return MutableList<Event>
     */
    fun getEventsFromDatabase(): MutableList<Event> {
        val collection = getDatabase().find()

        return collection.map { doc ->
            val eventDoc = doc.get("event", Document::class.java)
            Event()
                .id(eventDoc.getString("id"))
                .title(eventDoc.getString("title"))
                .date(eventDoc.getString("date"))
                .userid(eventDoc.getString("userid"))
        }.toMutableList()
    }

    fun getEventsForUserFromDatabase(): MutableList<Event> {
        val collection = getDatabase().find()
        val userid = jwt.claim<String>("ID").orElse(null)

        return collection.mapNotNull { doc ->
            val eventDoc = doc.get("event", Document::class.java)
            val mappedEvent = mapEvent(eventDoc)

            if (mappedEvent.userid == userid) {
                Event()
                    .id(eventDoc.getString("id"))
                    .title(eventDoc.getString("title"))
                    .date(eventDoc.getString("date"))
                    .userid(eventDoc.getString("userid"))
            } else {
                null
            }
        }.toMutableList()
    }
}