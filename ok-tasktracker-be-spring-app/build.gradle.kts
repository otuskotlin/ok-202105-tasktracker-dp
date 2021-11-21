import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.keycloak:keycloak-spring-boot-starter:15.0.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.5.6")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.kafka:spring-kafka:2.7.7")

    implementation("com.auth0:java-jwt:3.18.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(kotlin("test-junit"))

    implementation(project(":ok-tasktracker-be-common"))
    implementation(project(":ok-tasktracker-be-transport-openapi"))
    implementation(project(":ok-tasktracker-be-mappers-openapi"))
    implementation(project(":ok-tasktracker-be-stubs"))
    implementation(project(":ok-tasktracker-be-service-openapi"))
    implementation(project(":ok-tasktracker-be-logic"))
    implementation(project(":ok-tasktracker-be-repo-inmemory"))
    implementation(project(":ok-tasktracker-be-logging"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    bootBuildImage {
        imageName = "${project.name}:${project.version}"
    }
}