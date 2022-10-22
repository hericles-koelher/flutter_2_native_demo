package com.example.flutter_2_native_demo.repositories

interface PokemonImageRepository {
    fun getImage(pokemonIndex: Int): ByteArray
}
