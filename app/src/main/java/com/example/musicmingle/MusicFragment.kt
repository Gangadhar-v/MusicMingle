package com.example.musicmingle

import android.content.Intent
import android.os.Bundle
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicmingle.databinding.FragmentMusicBinding
import com.example.musicmingle.repo.MusicRepository
import com.example.musicmingle.viewModel.MusicViewModel
import com.example.musicmingle.viewModel.MusicViewModelFactory

class MusicFragment : Fragment() {

    lateinit var musicViewModel: MusicViewModel
    lateinit var binding: FragmentMusicBinding
    lateinit var musicAdapter: MusicAdapter
    lateinit var recyclerView:RecyclerView
    lateinit var progressBar: ProgressBar

    //    private var lastClickTime: Long = 0
    //onCreate is called to initialize the fragment and we initialize non-ui components
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = MusicRepository()
        val factory = MusicViewModelFactory(repository)
        musicViewModel =
            ViewModelProvider(this@MusicFragment, factory).get(MusicViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMusicBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.progressBar
        recyclerView = binding.musicRecyclerView

        val artist = arguments?.getString("artist").toString()
        binding.artistName.text = "Playlist of $artist"
        musicViewModel.musicData.observe(viewLifecycleOwner, Observer {
            musicAdapter = MusicAdapter(it.data, requireContext()){ url, isPlaying ->
                onMusicItemClick(url,isPlaying)
            }
            recyclerView.adapter = musicAdapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        })
        musicViewModel.error.observe(viewLifecycleOwner, { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
        if (artist.isNotEmpty()) {
            musicViewModel.fetchMusicData(artist)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

    }

       private fun onMusicItemClick(url: String,isPlaying:Boolean) {
           // Stop the currently playing music

           if (isPlaying) {
               val stopIntent = Intent(requireContext(), MusicService::class.java).apply {
                   action = MusicService.ACTION_STOP
               }
               requireContext().startService(stopIntent)

               // Play the selected music
               val playIntent = Intent(requireContext(), MusicService::class.java).apply {
                   action = MusicService.ACTION_PLAY
                   putExtra(MusicService.EXTRA_URL, url)
               }
               requireContext().startService(playIntent)
           } else {
               val pauseIntent = Intent(requireContext(), MusicService::class.java).apply {
                   action = MusicService.ACTION_PAUSE
               }
               requireContext().startService(pauseIntent)
           }
       }
}
