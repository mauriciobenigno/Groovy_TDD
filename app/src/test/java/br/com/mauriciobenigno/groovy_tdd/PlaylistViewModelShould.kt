package br.com.mauriciobenigno.groovy_tdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mauriciobenigno.groovy_tdd.utils.MainCoroutineScopeRule
import br.com.mauriciobenigno.groovy_tdd.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PlaylistViewModelShould {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val viewModel: PlaylistViewModel
    val repository: PlaylistRepository = mock()

    init {
        viewModel = PlaylistViewModel(repository)

    }

    @Test
    fun getPlaylistsFromRepository() {
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

}