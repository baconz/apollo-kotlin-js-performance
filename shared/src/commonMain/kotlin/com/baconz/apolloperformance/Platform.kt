package com.baconz.apolloperformance

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform