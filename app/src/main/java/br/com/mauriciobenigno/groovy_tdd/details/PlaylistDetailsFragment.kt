package br.com.mauriciobenigno.groovy_tdd.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.mauriciobenigno.groovy_tdd.databinding.FragmentPlaylistDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    lateinit var viewModel : PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory : PlaylistDetailsViewModelFactory


    val args: PlaylistDetailsFragmentArgs by navArgs()

    private lateinit var _binding: FragmentPlaylistDetailBinding
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        val id = args.playlistId

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistDetailsViewModel::class.java)


        viewModel.getPlaylistDetails(id)

        observeLiveData()

        return _binding.root
    }

    private fun observeLiveData() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->

            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                // TODO
            }

        }
    }

    private fun setupUI(playlistDetails: Result<PlaylistDetails>) {
        _binding.playlistName.text = playlistDetails.getOrNull()!!.name
        _binding.playlistDetails.text = playlistDetails.getOrNull()!!.details
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlaylistDetailsFragment().apply {

            }
    }
}