import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds/
 */

group = "CHANGEME"
version = "0.0.1"

val kotlinVersion = "1.8.10"
val junitVersion = "5.9.2"

plugins {
  kotlin("jvm") version "1.7.22"
  id("org.jetbrains.dokka") version "1.8.10"
  java
  `maven-publish`
}

repositories {
  mavenLocal()
  mavenCentral()
}

dependencies {
  implementation("io.github.microutils:kotlin-logging:3.0.5")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  implementation("org.slf4j:slf4j-simple:2.0.6")
  implementation(kotlin("reflect", kotlinVersion))
  implementation(kotlin("stdlib-jdk8", kotlinVersion))
  testImplementation("com.natpryce:hamkrest:1.8.0.1")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine:$junitVersion")
  testImplementation(kotlin("test", kotlinVersion))
  testImplementation(kotlin("test-junit", kotlinVersion))
}

val mainSrcSet = sourceSets["main"]

val sourceJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles Kotlin sources"
  archiveClassifier.set("sources")
  from(mainSrcSet.allSource)
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.17"
  }
}

val dokka by tasks.creating(org.jetbrains.dokka.gradle.DokkaTask::class)

val dokkaJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles Kotlin docs with Dokka"
  archiveClassifier.set("javadoc")
  from(dokka)
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

publishing {
  publications {
    create("default", MavenPublication::class.java) {
      from(components["java"])
      artifact(dokkaJar)
      artifact(sourceJar)
    }
  }
  repositories {
    maven {
      url = uri("$buildDir/repository")
    }
  }
}
