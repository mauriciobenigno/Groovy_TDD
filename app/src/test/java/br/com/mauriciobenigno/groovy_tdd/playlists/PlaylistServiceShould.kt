package br.com.mauriciobenigno.groovy_tdd.playlists

import br.com.mauriciobenigno.groovy_tdd.Playlist
import br.com.mauriciobenigno.groovy_tdd.PlaylistAPI
import br.com.mauriciobenigno.groovy_tdd.PlaylistService
import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service : PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<Playlist> = mock()

    @Test
    fun fecthPlaylistsFromAPI() = runBlockingTest {

        service = PlaylistService(api)

        service.fetchPlaylists()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test

}