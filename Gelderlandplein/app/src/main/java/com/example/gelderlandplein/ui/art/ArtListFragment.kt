package com.example.gelderlandplein.ui.art

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.ArtAdapter
import com.example.gelderlandplein.dummy.Art
import kotlinx.android.synthetic.main.fragment_art_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val REQ_ART_KEY = "req_art"
const val BUNDLE_ART_KEY = "bundle_art"

/**
 * A simple [Fragment] subclass.
 * Use the [ArtFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtFragment : Fragment(), ArtAdapter.OnArtCardViewClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val dummyArtItems = arrayListOf<Art>()
    private val artAdapter = ArtAdapter(dummyArtItems, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        for (i in Art.artTitles.indices) {
            dummyArtItems.add(Art(Art.artTitles[i], Art.artImages[i]))
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
    }

    override fun onCardViewClick(dummyArt: Art, position: Int) {
        goToDetail(dummyArt)
    }

    private fun goToDetail(art: Art) {
        setFragmentResult(REQ_ART_KEY, bundleOf(Pair(BUNDLE_ART_KEY, Art(art.title, art.image))))
        findNavController().navigate(R.id.action_ArtOverviewFragment_to_ArtDetailFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment thirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArtFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}