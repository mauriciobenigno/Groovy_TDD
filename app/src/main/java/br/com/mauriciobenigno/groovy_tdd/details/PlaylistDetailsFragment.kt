package br.com.mauriciobenigno.groovy_tdd.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.mauriciobenigno.groovy_tdd.R
import br.com.mauriciobenigno.groovy_tdd.databinding.FragmentPlaylistDetailBinding
import com.google.android.material.snackbar.Snackbar
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        val id = args.playlistId

        setupViewModel()

        viewModel.getPlaylistDetails(id)

        observeListLiveData()

        observeLoader()

        return _binding.root
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistDetailsViewModel::class.java)
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> _binding.detailsLoader.visibility = View.VISIBLE
                else -> _binding.detailsLoader.visibility = View.GONE
            }
        }
    }

    private fun observeListLiveData() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->

            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                Snackbar.make(_binding.playlistsDetailsRoot, R.string.generic_error, Snackbar.LENGTH_LONG).show()
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