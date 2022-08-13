package br.com.mauriciobenigno.groovy_tdd.details

import br.com.mauriciobenigno.groovy_tdd.playlist.PlaylistAPI
import br.com.mauriciobenigno.groovy_tdd.playlist.PlaylistRaw
import br.com.mauriciobenigno.groovy_tdd.playlist.PlaylistService
import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class PlaylistDetailsServiceShould : BaseUnitTest() {

    private lateinit var service : PlaylistDetailsService
    private val api: PlaylistDetailsAPI = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val exception = RuntimeException("Malditos desenvolvedores backend!!")

    private val id = "1"

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
        mockSuccessfulCase()

        service.fetchPlaylistDetais(id).single()

        verify(api, times(1)).fetchPlaylistDetail(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetais(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest{
        mockErrorCase()

        assertEquals("Algo errado nao esta certo",
            service.fetchPlaylistDetais(id).single().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchPlaylistDetail(id)).thenThrow(exception)

        service = PlaylistDetailsService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPlaylistDetail(id)).thenReturn(playlistDetails)

        service = PlaylistDetailsService(api)
    }
}