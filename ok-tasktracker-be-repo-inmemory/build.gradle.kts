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
    implementation("org.ehcache:ehcache:3.9.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")

    implementation(project(":ok-tasktracker-be-common"))
    testImplementation(project(":ok-tasktracker-be-repo-test"))
}
