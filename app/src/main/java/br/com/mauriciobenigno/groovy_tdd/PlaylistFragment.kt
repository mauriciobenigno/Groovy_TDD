package br.com.mauriciobenigno.groovy_tdd

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import br.com.mauriciobenigno.groovy_tdd.databinding.FragmentPlaylistBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel
    private lateinit var _binding: FragmentPlaylistBinding
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)


        setupViewModel()

        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when(loading){
                true -> _binding.loader.visibility = View.VISIBLE
                else -> _binding.loader.visibility = View.GONE
            }
        })

        viewModel.playlists.observe(this as LifecycleOwner, { playlists ->

            if(playlists.getOrNull() != null){
                setupList(_binding, playlists.getOrNull()!!)
            } else {
                // TODO
            }

        })

        return _binding.root
    }

    private fun setupList(
        bindingView: FragmentPlaylistBinding,
        playlists: List<Playlist>
    ) {
        with(bindingView.playlistsList as RecyclerView) {
            layoutManager = LinearLayoutManager(context)

            adapter = MyPlaylistRecyclerViewAdapter(playlists)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment()/*.apply {

            }*/
    }
}