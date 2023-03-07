package com.baconz.apolloperformance

import com.baconz.gql.jsei.AllFilmsQueryData
import com.baconz.gql.jsei.AllFilmsQueryFilms
import kotlinx.coroutines.*
import kotlin.js.Promise

@JsExport
fun getAllFilmsAsKotlinPromise(): Promise<Array<Film>> {
    return GlobalScope.async {
        getAllFilms()
    }.asPromise()
}

@JsModule("@apollo/client")
@JsNonModule
external object ApolloClientJs
external interface AllFilmsQueryResponse {
    val data: AllFilmsQueryData?
    val errors: Array<Any>?
}

@JsExport
fun getAllFilmsNativeJsPromise(): Promise<AllFilmsQueryResponse> {
    val holder = ApolloClientJs
    return js(
        """
            var link = ApolloClientJs.createHttpLink({uri: "https://swapi-graphql.netlify.app/.netlify/functions/index"});
            var ALL_FILMS = ApolloClientJs.gql("query AllFilmsQuery {allFilms {films {title director releaseDate speciesConnection { species { name classification homeworld { name }}}}}}");
            var client = new ApolloClientJs.ApolloClient({link: link, cache: new ApolloClientJs.InMemoryCache()})
            client.query({query: ALL_FILMS, fetchPolicy: 'no-cache',});
        """
    ).unsafeCast<Promise<AllFilmsQueryResponse>>()
}


@JsExport
fun getAllFilmsNativeAndMungeData(): Promise<Array<Film>> {
    return GlobalScope.async {
        val queryData = getAllFilmsNativeJsPromise().await()
        (queryData.data?.allFilms?.films as Array<AllFilmsQueryFilms?>?)?.filterNotNull()
            ?.map { Film(it.title, it.director, it.releaseDate) }?.toTypedArray() ?: emptyArray()
    }.asPromise()
}
