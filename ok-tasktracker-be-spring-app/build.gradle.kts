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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.kafka:spring-kafka:2.7.7")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation(project(":ok-tasktracker-be-common"))
    implementation(project(":ok-tasktracker-be-transport-openapi"))
    implementation(project(":ok-tasktracker-be-mappers-openapi"))
    implementation(project(":ok-tasktracker-be-stubs"))
    implementation(project(":ok-tasktracker-be-service-openapi"))
    implementation(project(":ok-tasktracker-be-logic"))
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