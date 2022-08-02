package br.com.mauriciobenigno.groovy_tdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mauriciobenigno.groovy_tdd.utils.MainCoroutineScopeRule
import br.com.mauriciobenigno.groovy_tdd.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PlaylistViewModelShould {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)


    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {

        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow{
                    emit(expected)
                }
            )
        }

        val viewModel = PlaylistViewModel(repository)

        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {

        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow{
                    emit(expected)
                }
            )
        }

        val viewModel = PlaylistViewModel(repository)

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

}