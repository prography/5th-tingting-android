package com.tingting.ver01.view

interface Subject {
    fun register(observer: Observer)
    fun unregister(observer: Observer)
    fun notifyObservers()
}