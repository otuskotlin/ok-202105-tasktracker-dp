plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":ok-tasktracker-be-common"))
    implementation(project(":ok-tasktracker-be-transport-openapi"))
    implementation(project(":ok-tasktracker-be-mappers-openapi"))
    implementation(project(":ok-tasktracker-be-stubs"))
}