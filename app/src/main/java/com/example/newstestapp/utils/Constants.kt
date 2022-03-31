package com.example.newstestapp.utils

import com.example.newstestapp.ui.adapters.Item

object Constants {

    enum class RequestsCode(val code: Int) {
        GoogleSignIn(1)
    }

    enum class NewsCategory(val param: String): Item {
        General("General"),
        Business("Business"),
        Entertainment("Entertainment"),
        Health("Health"),
        Science("Science"),
        Sports("Sports"),
        Technology("Technology")
    }

    enum class ChannelsName{
        GoogleQualifier
    }
}