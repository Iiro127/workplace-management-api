package com.example.api

import com.example.controller.ProjectsController
import jakarta.enterprise.context.Initialized
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.api.ProjectsApi
import src.gen.java.org.openapitools.model.Project

@RequestScoped
class ProjectsApi: ProjectsApi {

    @Inject
    lateinit var projectsController: ProjectsController
    override fun createProject(project: Project?): Project {
        return try {
            if (projectsController.createProject(project!!)){
                project
            } else {
                Project()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getProjects(): MutableList<Project> {
        return try {
            projectsController.getProjects()
        } catch (e: Exception){
            throw e
        }
    }
}