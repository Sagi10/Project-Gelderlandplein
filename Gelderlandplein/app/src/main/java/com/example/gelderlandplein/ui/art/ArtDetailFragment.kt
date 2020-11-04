package com.example.gelderlandplein.ui.art

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.gelderlandplein.R
import com.example.gelderlandplein.dummy.Art
import kotlinx.android.synthetic.main.item_detail_art.*

class ArtDetailFragment : Fragment() {

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

        observeFragmentResult()
    }

    private fun observeFragmentResult(){
        setFragmentResultListener(REQ_ART_KEY) { key, bundle ->
            bundle.getParcelable<Art>(BUNDLE_ART_KEY)?.let {
                iv_item_detail.setImageResource(it.image)
                tv_item_detail.text = it.title
            }
        }
    }
}