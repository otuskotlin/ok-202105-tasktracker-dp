rootProject.name = "tasktracker"

pluginManagement {
    val kotlinVersion: String by settings
    val openApiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion

        id("org.openapi.generator") version openApiVersion
    }
}

include("ok-tasktracker-be-common")
include("ok-tasktracker-mp-common")
include("ok-tasktracker-be-transport-openapi")
include("ok-marketplace-be-mappers-openapi")
