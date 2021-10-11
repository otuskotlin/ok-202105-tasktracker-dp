rootProject.name = "tasktracker"

pluginManagement {
    val kotlinVersion: String by settings
    val openApiVersion: String by settings

    val springBootVersion: String by settings
    val springDependencyVersion: String by settings
    val springPluginVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion

        id("org.openapi.generator") version openApiVersion

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion
        kotlin("plugin.spring") version springPluginVersion
    }
}

include("ok-tasktracker-be-common")
include("ok-tasktracker-be-spring-app")
include("ok-tasktracker-be-transport-openapi")
include("ok-tasktracker-be-mappers-openapi")
include("ok-tasktracker-be-stubs")
include("ok-tasktracker-be-service-openapi")
include("ok-tasktracker-be-common-cor")

