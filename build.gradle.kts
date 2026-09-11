plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.app.sberjv4_reliabilty.app"
version = "0.0.1-SNAPSHOT"
description = "SberJv4_Reliabilty"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-restclient:4.1.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.github.tomakehurst:wiremock-standalone:3.0.1")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    implementation("org.apache.httpcomponents.client5:httpclient5")
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.springframework.boot:spring-boot-starter-cache")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
