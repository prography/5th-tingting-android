package com.example.tintint_jw.new_package.util.extension

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

fun <T> Single<T>.with(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()
fun <T> Single<T>.commonRx() =
    this.observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { isLoading.onNext(true) }
        .doOnSuccess { isLoading.onNext(false) }
        .doOnError { isLoading.onNext(false) }
        .subscribeOn(Schedulers.io())