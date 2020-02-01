package com.tingting.ver01.MakeTeam

interface TagsListener {

    fun onTagCreated(tag: String)
    fun onTagRemoved(index:Int)
}