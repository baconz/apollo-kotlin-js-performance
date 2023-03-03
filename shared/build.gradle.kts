plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        useCommonJs()
        browser {
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting {
        }
    }
}
