import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://www.jitpack.io")
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
    implementation("com.squareup.retrofit2:converter-jaxb:2.9.0")
//    implementation("com.tickaroo.tikxml:annotation:0.8.15")
//    implementation("com.tickaroo.tikxml:core:0.8.15")
//    kapt("com.github.Tickaroo.tikxml:processor-common:0.8.15")
//    kapt("com.tickaroo.tikxml:processor:0.8.15")
//    kapt("com.tickaroo.tikxml:auto-value-tikxml:0.8.15")
//    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.15")

    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.4.0")
    // https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager
    implementation(group="io.github.bonigarcia", name="webdrivermanager", version="5.3.0")

    // log
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    // gson
    implementation("com.google.code.gson:gson:2.9.1")

    kapt("org.projectlombok:lombok:1.18.24")
    implementation("org.projectlombok:lombok:1.18.24")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}