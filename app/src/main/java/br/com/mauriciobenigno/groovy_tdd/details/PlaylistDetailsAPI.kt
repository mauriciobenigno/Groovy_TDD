package br.com.mauriciobenigno.groovy_tdd.details

import retrofit2.http.GET
import javax.inject.Inject

class PlaylistDetailsAPI @Inject constructor() {

    //@GET("playlists")
    //suspend fun fetchPlaylistDetail(id: String) : PlaylistDetails

    @GET("playlists")
    suspend fun fetchPlaylistDetail(id: String) : PlaylistDetails {
        TODO()
    }

}
