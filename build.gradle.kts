plugins {
    id("java")
}

group = "org.game"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-cli:commons-cli:1.9.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    implementation("com.google.code.gson:gson:2.12.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.17.0")
    testImplementation("org.mockito:mockito-core:5.17.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("fatJar") {
    manifest {
        attributes("Main-class" to "org.game.Main")
    }
    archiveBaseName.set("gameTestTask-all")
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
    duplicatesStrategy = DuplicatesStrategy.INCLUDE // Стратегия обработки дубликатов
}