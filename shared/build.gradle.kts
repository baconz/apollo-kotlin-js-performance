plugins {
    kotlin("multiplatform")
    // kotlin("js")
    id("com.apollographql.apollo3")
}
repositories {
    mavenLocal()
    google()
    mavenCentral()
}

kotlin {
    js(IR) {
        useCommonJs()
        browser {
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(deps.kotlinx.coroutines.core)

                api(deps.apollo.adapters)
                api(deps.apollo.api)
                api(deps.apollo.runtime)
            }
        }
        val commonTest by getting {
        }
        val jsMain by getting {
            dependencies {
                api("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-pre.515")

                // implementation(npm("benchmark", "2.1.2", true))
                // implementation(npm("@apollo/client", "3.7.9", true))
                // implementation(npm("graphql", "16.6.0"))
                // implementation(npm("react", "18.2.0"))
            }
        }
    }
}
apollo {
    service("perf-test") {
        packageName.set("com.baconz.gql")
        generateAsInternal.set(false)
        generateOptionalOperationVariables.set(false)
        codegenModels.set("responseBased")
        jsExport.set(true)
//
//        mapScalar("GraphQLBoolean", "kotlin.Boolean")
//        mapScalar("GraphQLFloat", "kotlin.Float")
//        mapScalar("GraphQLID", "kotlin.String")
//        mapScalar("GraphQLInt", "kotlin.Int")
//        mapScalar("GraphQLString", "kotlin.String")
    }
}

