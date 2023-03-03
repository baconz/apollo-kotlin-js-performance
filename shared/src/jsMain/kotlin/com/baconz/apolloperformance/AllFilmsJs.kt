package com.baconz.apolloperformance

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

@JsExport
fun getAllFilmsNativeJsPromise(): Any {
    val holder = ApolloClientJs
    return js(
        """
            var link = ApolloClientJs.createHttpLink({uri: "https://swapi-graphql.netlify.app/.netlify/functions/index"});
            var ALL_FILMS = ApolloClientJs.gql("query AllFilmsQuery {allFilms {films {title director releaseDate speciesConnection { species { name classification homeworld { name }}}}}}");
            var client = new ApolloClientJs.ApolloClient({link: link, cache: new ApolloClientJs.InMemoryCache()})
            client.query({query: ALL_FILMS, fetchPolicy: 'no-cache',});
        """
    ).unsafeCast<Any>()
}
