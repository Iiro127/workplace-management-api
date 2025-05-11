package com.example.controller

import com.example.db.EventsDatabaseController
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.model.Event

@ApplicationScoped
class EventsController {

    @Inject
    lateinit var databaseController: EventsDatabaseController

    fun createEvent(event: Event?): Boolean {
        return databaseController.addEventToDatabase(event)
    }

    fun deleteEvent(eventid: String): Boolean {
        return databaseController.deleteEventFromDatabase(eventid)
    }

    fun getEvents(): MutableList<Event> {
        return databaseController.getEventsFromDatabase()
    }

    fun getUserEvents(): MutableList<Event> {
        return databaseController.getEventsForUserFromDatabase()
    }
}