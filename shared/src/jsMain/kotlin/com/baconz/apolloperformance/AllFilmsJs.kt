//package com.baconz.apolloperformance
//
//import com.apollographql.apollo3.api.Operation
//import com.apollographql.apollo3.api.composeJsonRequest
//import com.apollographql.apollo3.api.json.buildJsonString
//import com.apollographql.apollo3.api.unsafeCastOrCast
//import com.baconz.gql.AllFilmsQuery
//import com.baconz.gql.NodeQuery
//import com.baconz.gql.VehicleQuery
//import io.ktor.client.fetch.*
//import io.ktor.http.*
//import js.core.jso
//import kotlinx.coroutines.*
//import kotlin.js.Promise
//
//@JsExport
//fun getAllFilmsAsKotlinPromise(): Promise<Array<Film>> {
//    return GlobalScope.async {
//        getAllFilms()
//    }.asPromise()
//}
//
//@JsModule("@apollo/client")
//@JsNonModule
//external object ApolloClientJs
//
//@JsExport
//fun getAllFilmsNativeJsPromise(): Any {
//    val holder = ApolloClientJs
//    return js(
//        """
//            var link = ApolloClientJs.createHttpLink({uri: "https://swapi-graphql.netlify.app/.netlify/functions/index"});
//            var ALL_FILMS = ApolloClientJs.gql("query AllFilmsQuery {allFilms {films {title director releaseDate speciesConnection { species { name classification homeworld { name }}}}}}");
//            var client = new ApolloClientJs.ApolloClient({link: link, cache: new ApolloClientJs.InMemoryCache()})
//            client.query({query: ALL_FILMS, fetchPolicy: 'no-cache',});
//        """,
//    ).unsafeCast<Any>()
//}
//
//private suspend inline fun <reified D : Operation.Data> runOp(op: Operation<D>): D {
//    val body = buildJsonString {
//        op.composeJsonRequest(this)
//    }
//    val finalHeaders: Array<Array<String>> =
//        arrayOf(arrayOf(HttpHeaders.ContentType, "application/json"))
//
//    val fetchResponse = fetch(
//        "https://swapi-graphql.netlify.app/.netlify/functions/index",
//        toRequestInit(body, finalHeaders, HttpMethod.Post),
//    ).await()
//    val json = fetchResponse.json().await().asDynamic()
//    return json["data"].unsafeCast<D>()
//}
//
//private fun toRequestInit(
//    body: String?,
//    headers: Array<Array<String>>,
//    method: HttpMethod,
//): RequestInit {
//    return jso {
//        body?.let { this.body = body }
//        this.headers = headers
//        this.method = when (method) {
//            HttpMethod.Get -> HttpMethod.Get.value
//            HttpMethod.Post -> HttpMethod.Post.value
//            else -> {
//                throw RuntimeException("Unsupported method: $method")
//            }
//        }
//    }
//}
//
//@JsExport
//class BarOperation {
//    sealed interface Node {
//        val name: String
//    }
//
//    data class NumberedNode(override val name: String, val number: Int) : Node
//    data class BarResponse(val bar: String, val node: Node)
//}
//
//fun BarOperation.Node.asNumberedNode() = this.unsafeCast<BarOperation.NumberedNode>()
//
//@JsExport
//fun test() {
//    GlobalScope.async {
//        val d1 = runOp(AllFilmsQuery())
//        console.log(d1)
//
//        val d2 = runOp(NodeQuery("dmVoaWNsZXM6NA=="))
//        console.log(d2.node?.unsafeCastOrCast<NodeQuery.Data.VehicleNode>()?.cargoCapacity)
//
//        val d3 = runOp((VehicleQuery("dmVoaWNsZXM6NA==")))
//        console.log(d3.vehicle?.cargoCapacity)
//    }.asPromise()
//}
//
//data class Point(val x: Int, val y: Int)
//
//@JsExport
//fun parseJs() {
//    val point = jso<dynamic> {
//        x = 10
//        y = 10
//    }
//
//    val typedPoint = point.unsafeCast<Point>()
//
//    val responseBody = js("""{bar: "baz", node: {name: "bob", number: 1}}""")
//    val a: BarOperation.BarResponse = responseBody.unsafeCast<BarOperation.BarResponse>() // jso {
//    console.log("numbered", a.bar, a.node.asNumberedNode().number)
//}
//
//public data class VehicleBad(
//    /**
//     * The name of this vehicle. The common name, such as "Sand Crawler" or "Speeder
//     * bike".
//     */
//    public val name: String?,
//    /**
//     * The maximum number of kilograms that this vehicle can transport.
//     */
//    public val cargoCapacity: Double?,
//)
//
//internal interface Person {
//    val name: String
//}
//
//@JsExport
//internal data class VehicleGood(
//
//    public val drive: Person,
//
//    /**
//     * The name of this vehicle. The common name, such as "Sand Crawler" or "Speeder
//     * bike".
//     */
//    public val name: String?,
//    /**
//     * The maximum number of kilograms that this vehicle can transport.
//     */
//    public val cargoCapacity: Double?,
//) {
//    data class SubVehicleGood(val trunk: String)
//    public companion object {
//        const val FOO = "123"
//    }
//}
//
//external interface VehicleExternal {
//    val name: String?
//    val cargoCapacity: Double?
//}
//
//fun foo() {
//    "a".asDynamic().unsafeCast<VehicleExternal>()
//}
