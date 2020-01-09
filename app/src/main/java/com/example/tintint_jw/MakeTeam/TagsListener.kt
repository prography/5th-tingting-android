package com.example.tintint_jw.MakeTeam

interface TagsListener {

    fun onTagCreated(tag: String)
    fun onTagRemoved(index:Int)
}