package br.com.mauriciobenigno.groovy_tdd.playlists

import br.com.mauriciobenigno.groovy_tdd.Playlist
import br.com.mauriciobenigno.groovy_tdd.PlaylistRepository
import br.com.mauriciobenigno.groovy_tdd.PlaylistService
import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service : PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Algo está errado")

    @Test
    fun getsPlaylistFromService() = runBlockingTest{

        val repository = PlaylistRepository(service)
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {

        val repository = mockSucessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())

    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )

        val repository = PlaylistRepository(service)
        return repository
    }

    private suspend fun mockSucessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        val repository = PlaylistRepository(service)
        return repository
    }
}