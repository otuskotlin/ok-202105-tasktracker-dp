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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
    implementation("junit:junit:4.13.1")

    testImplementation(kotlin("test-junit"))

    implementation(project(":ok-tasktracker-be-common"))
}
