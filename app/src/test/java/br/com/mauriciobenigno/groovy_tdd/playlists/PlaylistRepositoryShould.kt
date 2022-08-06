package br.com.mauriciobenigno.groovy_tdd.playlists

import br.com.mauriciobenigno.groovy_tdd.PlaylistRepository
import br.com.mauriciobenigno.groovy_tdd.PlaylistService
import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service : PlaylistService = mock()

    @Test
    fun getsPlaylistFromService() = runBlockingTest{

        val repository = PlaylistRepository(service)
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

}