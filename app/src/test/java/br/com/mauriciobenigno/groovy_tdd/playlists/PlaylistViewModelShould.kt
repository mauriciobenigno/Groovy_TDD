package br.com.mauriciobenigno.groovy_tdd.playlists

import br.com.mauriciobenigno.groovy_tdd.Playlist
import br.com.mauriciobenigno.groovy_tdd.PlaylistRepository
import br.com.mauriciobenigno.groovy_tdd.PlaylistViewModel
import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import br.com.mauriciobenigno.groovy_tdd.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")


    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {

        val viewModel = mockSucessfulCase()

        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {

        val viewModel = mockSucessfulCase()

        // Adicionado um Get para o valor, uma vez que o 'getValueForTest' retorna também o valor, acima de sucess
        assertEquals(expected.getOrNull(), viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiverError(){
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }

        val viewModel = PlaylistViewModel(repository)

        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    private fun mockSucessfulCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        val viewModel = PlaylistViewModel(repository)
        return viewModel
    }

}