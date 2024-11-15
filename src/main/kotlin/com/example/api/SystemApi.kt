package com.example.api

import jakarta.enterprise.context.RequestScoped
import src.gen.java.org.openapitools.api.SystemApi

@RequestScoped
class SystemApi: SystemApi {
    override fun ping(): String {
        return "Pong"
    }
}