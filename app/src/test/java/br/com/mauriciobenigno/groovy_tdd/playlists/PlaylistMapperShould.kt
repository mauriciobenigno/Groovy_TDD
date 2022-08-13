package br.com.mauriciobenigno.groovy_tdd.playlists

import br.com.mauriciobenigno.groovy_tdd.playlist.PlaylistMapper
import br.com.mauriciobenigno.groovy_tdd.playlist.PlaylistRaw
import br.com.mauriciobenigno.groovy_tdd.R
import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals

import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "da name", "da category")
    private val playlistRawRock = PlaylistRaw("1", "da name", "da category")

    private val mapper = PlaylistMapper()

    private val playlists = mapper(listOf(playlistRaw))

    private val playlist = playlists[0]
    private val playlistRock = mapper(listOf(playlistRawRock))[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageNotRock(){
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapdRockImageWhenRockCategory(){
        assertEquals(R.mipmap.rock, playlistRock.image)
    }

}
