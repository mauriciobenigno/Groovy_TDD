package br.com.mauriciobenigno.groovy_tdd.playlist

import retrofit2.http.GET

interface PlaylistAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists() : List<PlaylistRaw>

    @GET("playlists")
    suspend fun fetchPlaylistDetail(id: String) : List<PlaylistRaw>

}
