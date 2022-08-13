package br.com.mauriciobenigno.groovy_tdd.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(
    private val service: PlaylistDetailsService
) : ViewModel() {

    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            service.fetchPlaylistDetais(id)
                .collect {
                    playlistDetails.postValue(it)
                }
        }
    }

}
