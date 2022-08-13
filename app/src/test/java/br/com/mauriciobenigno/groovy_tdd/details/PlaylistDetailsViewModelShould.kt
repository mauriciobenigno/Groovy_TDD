package br.com.mauriciobenigno.groovy_tdd.details

import br.com.mauriciobenigno.groovy_tdd.utils.BaseUnitTest
import br.com.mauriciobenigno.groovy_tdd.utils.captureValues
import br.com.mauriciobenigno.groovy_tdd.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class PlaylistDetailsViewModelShould  : BaseUnitTest() {

    lateinit var viewModel: PlaylistDetailsViewModel

    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistDetails = mock()

    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Algo errado nao esta certo")
    private val error = Result.failure<PlaylistDetails>(exception)


    private val id = "1"


    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest{

        mockSucessfulCase()

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetais(id)

    }

    @Test
    fun emitPlaylistDetailsFromService() = runBlockingTest{

        mockSucessfulCase()

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest{
        mockErrorCase()

        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        mockSucessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailsLoaded() = runBlockingTest {
        mockSucessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)

            viewModel.playlistDetails.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockErrorCase() {
        whenever(service.fetchPlaylistDetais(id)).thenReturn(
            flow {
                emit(error)
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

    private suspend fun mockSucessfulCase() {
        whenever(service.fetchPlaylistDetais(id)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }
}