package com.example.gelderlandplein.dummy

import android.os.Parcelable
import com.example.gelderlandplein.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class Event(

    var title: String,
    var image: Int,
    var actieGeldig: String?
) : Parcelable {

    companion object {
        val titles = arrayListOf(
            "Event 1",
            "Event 2",
            "Event 3",
            "Event 4",
            "Event 5",
            "Event 6",
            "Event 7",
            "Event 8",
            )

        val images = arrayListOf(
            R.drawable.example_event1,
            R.drawable.example_art2,
            R.drawable.example_event1,
            R.drawable.example_art2,
            R.drawable.example_event1,
            R.drawable.example_art2,
            R.drawable.example_event1,
            R.drawable.example_art2,
        )
    }
}