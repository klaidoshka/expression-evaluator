plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

group = "com.github.klaidoshka"
version = "1.0"

repositories { mavenCentral() }
dependencies { implementation(kotlin("stdlib-jdk8")) }
kotlin { jvmToolchain(21) }

publishing {
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}