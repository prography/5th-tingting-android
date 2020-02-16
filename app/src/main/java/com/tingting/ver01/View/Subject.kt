package com.tingting.ver01.View

import java.util.*

interface Subject {
    fun register(observer: Observer)
    fun unregister(observer: Observer)
    fun notifyObservers()
}