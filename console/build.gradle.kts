plugins {
    java
    id("org.springframework.boot") version "3.1.11"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ru.anastasiya"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


springBoot {
    mainClass.value("ru.anastasiya.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli-spring-boot-starter:4.7.5")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(project(":model"))
    implementation(project(":service"))
    implementation("org.springframework:spring-tx:5.3.22")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


