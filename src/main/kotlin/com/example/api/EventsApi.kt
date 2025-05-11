package com.example.api

import com.example.controller.EventsController
import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.api.EventsApi
import src.gen.java.org.openapitools.model.Event

@RequestScoped
@PermitAll
class EventsApi: EventsApi {

    @Inject
    lateinit var eventsController: EventsController

    override fun createEvent(event: Event?): Event {
        return try {
            if (eventsController.createEvent(event!!)){
                event
            } else {
                Event()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun deleteEvent(eventid: String?): Boolean {
        return try {
            eventsController.deleteEvent(eventid!!)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun findEvent(eventid: String?): Event {
        TODO("Not yet implemented")
    }

    override fun getEvents(): MutableList<Event> {
        return try {
            eventsController.getEvents()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getUserEvents(): MutableList<Event> {
        return try {
            eventsController.getUserEvents()
        } catch (e: Exception) {
            throw e
        }
    }
}