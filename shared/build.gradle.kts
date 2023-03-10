plugins {
    kotlin("multiplatform")
    id("com.apollographql.apollo3")
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
                implementation(npm("@apollo/client", "3.7.9", true))
                implementation(npm("graphql", "16.6.0"))
                implementation(npm("react", "18.2.0"))
            }
        }
    }
}
apollo {
    service("perf-test") {
        packageName.set("com.baconz.gql")
        generateAsInternal.set(true)
        generateOptionalOperationVariables.set(false)
//
//        mapScalar("GraphQLBoolean", "kotlin.Boolean")
//        mapScalar("GraphQLFloat", "kotlin.Float")
//        mapScalar("GraphQLID", "kotlin.String")
//        mapScalar("GraphQLInt", "kotlin.Int")
//        mapScalar("GraphQLString", "kotlin.String")
    }
}

