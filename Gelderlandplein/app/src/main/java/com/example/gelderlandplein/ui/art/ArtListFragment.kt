package com.example.gelderlandplein.ui.art

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.ArtAdapter
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_art_list.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import java.lang.Exception

const val REQ_ART_KEY = "req_art"
const val BUNDLE_ART_KEY = "bundle_art"

class ArtFragment : Fragment() {
    private val arts = arrayListOf<Art>()
    private val artAdapter = ArtAdapter(arts, ::goToDetailArt)
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseViewModel.getAllArts()

        if (arts.isNotEmpty()) {
            pb_loading_art.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_art_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_arts_list.adapter = artAdapter
        observeArts()
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
        setFragmentResult(
            REQ_ART_KEY,
            bundleOf(Pair(BUNDLE_ART_KEY, Art(art.name, art.image, art.beschrijving, art.artist)))
        )
        findNavController().navigate(R.id.action_ArtOverviewFragment_to_ArtDetailFragment)
    }
}