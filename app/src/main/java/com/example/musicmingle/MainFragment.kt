package com.example.musicmingle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.musicmingle.databinding.FragmentMainBinding
import com.example.musicmingle.repo.MusicRepository
import com.example.musicmingle.viewModel.MusicViewModel
import com.example.musicmingle.viewModel.MusicViewModelFactory


class MainFragment : Fragment() {

    lateinit var imageList: ArrayList<SlideModel>
    lateinit var binding: FragmentMainBinding
    lateinit var artist: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        imageList = ArrayList<SlideModel>() // Create image list


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList.add(SlideModel(R.drawable.img, "Music stimulates wisdom"))
        imageList.add(SlideModel(R.drawable.img_2, "Eminem playlist"))
        imageList.add(SlideModel(R.drawable.img_3, "Ariana Grande Melodies"))
        imageList.add(SlideModel(R.drawable.img_4, "Salena Gomez Songs"))
        imageList.add(SlideModel(R.drawable.img_5, "K J Yesudas"))
        imageList.add(SlideModel(R.drawable.img_6, "Baala Muralikrishna"))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        val count = binding.editText.editableText.count()
        if (count >= 0) {
            binding.edittextLyt.error = null
        }
        binding.searchBtn.setOnClickListener {
            artist = binding.editText.text.toString()
            if (artist.isNotEmpty() == true) {
                val bundle = Bundle()
                bundle.putString("artist", artist)
                it.findNavController().navigate(R.id.action_mainFragment_to_musicFragment, bundle)

            } else {
                binding.edittextLyt.error = "Enter Artist Name"
            }
        }
    }
}


