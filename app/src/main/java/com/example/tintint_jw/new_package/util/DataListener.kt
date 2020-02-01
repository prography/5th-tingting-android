package com.example.tintint_jw.new_package.util

interface DataListener<T> {
    fun onSuccess(data: T)
    fun onFail()
}