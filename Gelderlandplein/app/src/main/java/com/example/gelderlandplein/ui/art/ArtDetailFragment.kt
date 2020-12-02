package com.example.gelderlandplein.ui.art

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail_art.*

class ArtDetailFragment : Fragment() {

    private val firebaseViewModel: FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail_art, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeDetailResult()
    }

    private fun observeDetailResult(){
        firebaseViewModel.artDetail.observe(viewLifecycleOwner, {
            if (it.image.isNullOrEmpty()){
                iv_item_detail.setImageResource(R.drawable.image_not_found)
            } else Picasso.get().load(it.image).into(iv_item_detail)
            tv_item_detail_title.text = it.name
            tv_item_detail_beschrijving.text = it.beschrijving
            tv_item_detail_artist.text = it.artist
        })
    }
}