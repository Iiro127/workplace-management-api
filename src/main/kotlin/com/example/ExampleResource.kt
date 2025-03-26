package com.example

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class ExampleResource {



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("user")
    fun hello() = "Hello from Quarkus REST"
}