package com.baconz.apolloperformance

import com.apollographql.apollo3.ApolloClient
import com.baconz.gql.AllFilmsQuery

@JsExport
data class Film(
    /**
     * The title of this film.
     */
    val title: String?,
    /**
     * The name of the director of this film.
     */
    val director: String?,
    /**
     * The ISO 8601 date format of film release at original creator country.
     */
    val releaseDate: String?
)

internal suspend fun getAllFilms(): Array<Film> {
    val client = ApolloClient.Builder()
        .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index")
        .build()
    return client.query(AllFilmsQuery())
        .execute().dataAssertNoErrors.allFilms?.films?.filterNotNull()?.map {
            Film(it.title, it.director, it.releaseDate)
        }?.toTypedArray() ?: emptyArray()
}
