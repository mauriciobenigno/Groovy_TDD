package br.com.mauriciobenigno.groovy_tdd.details

import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailsAPI  {

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistDetail(@Path("id") id: String) : PlaylistDetails

}
