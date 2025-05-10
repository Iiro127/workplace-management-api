package com.example.api

import src.gen.java.org.openapitools.api.EventsApi
import src.gen.java.org.openapitools.model.Event

class EventsApi: EventsApi {
    override fun createEvent(event: Event?): Event {
        TODO("Not yet implemented")
    }

    override fun deleteEvent(eventid: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun findEvent(eventid: String?): Event {
        TODO("Not yet implemented")
    }

    override fun getEvents(): MutableList<Event> {
        TODO("Not yet implemented")
    }

    override fun getUserEvents(userid: String?): MutableList<Event> {
        TODO("Not yet implemented")
    }
}