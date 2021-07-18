rootProject.name = "tasktracker"

pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
    }
}

include("ok-tasktracker-be-common")
include("ok-tasktracker-mp-common")
