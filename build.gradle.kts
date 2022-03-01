plugins {
    kotlin("jvm") version "1.6.10"
}

group = "live.myoun.kcp"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))
}