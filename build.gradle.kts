import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // db
    implementation("org.hibernate:hibernate-core:5.6.1.Final")
    implementation("mysql:mysql-connector-java:8.0.30")
    // https://mvnrepository.com/artifact/com.h2database/h2
    implementation(group="com.h2database", name="h2", version="2.1.214")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation(group="com.squareup.retrofit2", name="converter-gson", version="2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // gson
    implementation("com.google.code.gson:gson:2.9.1")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("org.projectlombok:lombok:1.18.24")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}