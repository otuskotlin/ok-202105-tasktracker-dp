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
    implementation(project(":ok-tasktracker-be-common-cor"))
    implementation(project(":ok-tasktracker-be-common-validation"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
}