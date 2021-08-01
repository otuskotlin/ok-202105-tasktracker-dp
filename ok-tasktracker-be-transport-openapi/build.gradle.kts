plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    val jacksonVersion: String by project
    implementation(kotlin("stdlib"))
    api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

openApiGenerate {
    val basePackage = "${project.group}.transport.openapi.task"
    packageName.set(basePackage)
    generatorName.set("kotlin")
    apiPackage.set("$basePackage.api")
    invokerPackage.set("$basePackage.invoker")
    modelPackage.set("$basePackage.models")
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }
    configOptions.set(mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
    ))
    inputSpec.set("${rootProject.projectDir}/spec/spec-tasktracker-api-v1.0.0.yaml")
}
sourceSets {
    main {
        java.srcDirs("$buildDir/generate-resources/main/src/main/kotlin")
    }
    test {
        java.srcDirs("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

tasks {
    compileKotlin.get().dependsOn(openApiGenerate)
}