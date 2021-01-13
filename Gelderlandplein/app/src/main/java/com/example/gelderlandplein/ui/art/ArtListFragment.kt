package com.example.gelderlandplein.ui.art

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.ArtAdapter
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.fragment_art_list.*
import kotlinx.android.synthetic.main.fragment_event_list.*

class ArtFragment : Fragment() {
    private val arts = arrayListOf<Art>()
    private val artAdapter = ArtAdapter(arts, ::goToDetailArt)

    private val firebaseViewModel: FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arts.isNotEmpty()) {
            pb_loading_art.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        observeArts()
        return inflater.inflate(R.layout.fragment_art_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_arts_list.adapter = artAdapter
    }

    private fun observeArts() {
        firebaseViewModel.arts.observe(viewLifecycleOwner, {
            this@ArtFragment.arts.clear()
            this@ArtFragment.arts.addAll(it)
            pb_loading_art.isVisible = false
            artAdapter.notifyDataSetChanged()
        })
    }

    private fun goToDetailArt(art: Art) {
        firebaseViewModel.sendDetailArt(art)
        findNavController().navigate(R.id.action_ArtOverviewFragment_to_ArtDetailFragment)
    }
}