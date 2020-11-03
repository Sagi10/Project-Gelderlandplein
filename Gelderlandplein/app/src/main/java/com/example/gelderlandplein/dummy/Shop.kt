package com.example.gelderlandplein.dummy

import com.example.gelderlandplein.R

data class Shop(

    var name: String,
    var tag: String,
    var image: Int
) {
    companion object {
        val shopTitles = arrayListOf<String>(
            "Albert Heijn",
            "Etos",
            "Douglas",
            "Albert Heijn",
            "Etos",
            "Douglas",
        )

        val shopTags = arrayListOf<String>(
            "Supermarkt",
            "Drogist",
            "Parfumerie",
            "Supermarkt",
            "Drogist",
            "Parfumerie"
        )

        val shopImages = arrayListOf<Int>(
            R.drawable.example_shop1,
            R.drawable.example_shop2,
            R.drawable.example_shop3,
            R.drawable.example_shop1,
            R.drawable.example_shop2,
            R.drawable.example_shop3
        )
    }
}