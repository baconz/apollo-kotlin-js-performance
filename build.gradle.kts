buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(deps.apollo.gradle)
    }
}

plugins {
    kotlin("js").version(deps.versions.kotlin).apply(false)
}
