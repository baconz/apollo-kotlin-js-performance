buildscript {
    repositories {
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
