package com.example.flutter_2_native_demo.services

import com.example.flutter_2_native_demo.repositories.PokemonImageRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class PokemonImageServiceImpl(private val pokeRepository: PokemonImageRepository) : PokemonImageService {
    override fun getPokemonImageBytes(): Flowable<ByteArray> {
        return Flowable.interval(0, 2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .onBackpressureBuffer(2)
            .map { pokeRepository.getImage(Random.nextInt(778)) }
    }
}