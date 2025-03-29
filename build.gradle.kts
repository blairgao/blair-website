plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("com.github.node-gradle.node") version "7.0.1"
}

group = "com.blairgao"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// TypeScript configuration
node {
    version.set("20.11.1")
    npmVersion.set("10.2.4")
    download.set(true)
    nodeProjectDir.set(file("frontend"))
}

tasks.named<com.github.gradle.node.npm.task.NpmTask>("npmInstall") {
    group = "build"
    description = "Installs all dependencies from package.json"
    workingDir.set(file("frontend"))
    args.set(listOf("install"))
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmBuild") {
    group = "build"
    description = "Builds the TypeScript project"
    workingDir.set(file("frontend"))
    args.set(listOf("run", "build"))
    dependsOn("npmInstall")
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmCopyAssets") {
    group = "build"
    description = "Copies frontend assets"
    workingDir.set(file("frontend"))
    args.set(listOf("run", "copy-assets"))
    dependsOn("npmBuild")
}

tasks.register<Copy>("copyFrontendAssets") {
    group = "build"
    description = "Copies frontend assets to the static resources directory"
    from("frontend/dist")
    into("src/main/resources/static")
    dependsOn("npmCopyAssets")
}

tasks.named("processResources") {
    dependsOn("copyFrontendAssets")
} 