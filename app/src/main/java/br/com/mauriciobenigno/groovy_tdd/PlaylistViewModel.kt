package br.com.mauriciobenigno.groovy_tdd

import androidx.lifecycle.*

class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {
    val playlists = liveData<Result<List<Playlist>>> {
        emitSource(repository.getPlaylists().asLiveData())
    }

    /*
    @Deprecated("Alterado por chamada direta")
    val playlists = MutableLiveData<Result<List<Playlist>>>()

    init {
        viewModelScope.launch {
            repository.getPlaylists()
                .collect {
                    playlists.value = it
                }
        }
    }

    */
}

