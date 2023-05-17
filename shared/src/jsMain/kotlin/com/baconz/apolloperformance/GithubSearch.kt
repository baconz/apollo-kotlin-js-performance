package com.baconz.apolloperformance

import com.apollographql.apollo3.api.json.BufferedSourceJsonReader
import com.apollographql.apollo3.api.json.DynamicJsJsonReader
import com.apollographql.apollo3.api.parseJsonResponse
import com.baconz.gql.SearchQuery
import com.baconz.gql.type.SearchType
import okio.Buffer
import okio.BufferedSource

external fun atob(
    data: String,
): String

external class TextEncoder {
    fun encode(str: String): ByteArray
}

val string = atob(GITHUB_RESULT)
val bytes = TextEncoder().encode(string)
val operation = SearchQuery("foo", SearchType.ISSUE.rawValue, 100)
var buffer = Buffer().write(bytes)

@JsExport
fun resetBuffer() {
    buffer = Buffer().write(bytes)
}

@JsExport
fun parseWithJsExport() {
    val dynamic = JSON.parse<dynamic>(string)
    dynamic["data"].unsafeCast<SearchQuery.Data>()
}

@JsExport
fun parseWithDynamicParser() {
    val dynamic = JSON.parse<dynamic>(string)
    operation.parseJsonResponse(DynamicJsJsonReader(dynamic))
}

@JsExport
fun parseWithBytesParser() {
    resetBuffer()
    operation.parseJsonResponse(BufferedSourceJsonReader(buffer))
}