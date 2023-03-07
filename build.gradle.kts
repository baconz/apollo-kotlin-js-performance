buildscript {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        google()
        mavenCentral()
    }

    dependencies {
        classpath(deps.apollo.gradle)
        classpath("com.apollographql.jsei:apollo-jsei:0.0.1-SNAPSHOT")
    }
}

plugins {
    kotlin("js").version(deps.versions.kotlin).apply(false)
}
