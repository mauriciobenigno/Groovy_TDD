package br.com.mauriciobenigno.groovy_tdd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistViewModel : ViewModel() {
    val playlists = MutableLiveData<List<Playlist>>()
}

