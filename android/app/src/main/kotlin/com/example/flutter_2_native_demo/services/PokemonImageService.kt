package com.example.flutter_2_native_demo.services

import io.reactivex.rxjava3.core.Flowable

interface PokemonImageService {
    fun getPokemonImageBytes(): Flowable<ByteArray>
}