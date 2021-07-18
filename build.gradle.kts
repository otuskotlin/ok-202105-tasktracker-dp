plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "com.polyakovworkbox.otuskotlin.tasktracker"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}