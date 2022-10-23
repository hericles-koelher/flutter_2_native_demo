package com.example.flutter_2_native_demo.repositories

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class PokemonImageRepositoryImpl : PokemonImageRepository {
    private val retrofitClient: RetrofitPokemonImageRepository = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/")
        .build()
        .create(RetrofitPokemonImageRepository::class.java)

    override fun getImage(pokemonIndex: Int): ByteArray {
        val pokemonImageName = "${pokemonIndex.toString().padStart(3, '0')}.png"

        val response = retrofitClient.getImage(pokemonImageName).execute()

        if (response.isSuccessful) {
            return response.body()!!.bytes()
        } else {
            throw IOException("Falha ao realizar download da imagem '${pokemonImageName}'")
        }
    }
}

interface RetrofitPokemonImageRepository {
    @GET("images/{imageName}")
    fun getImage(@Path("imageName") imageName: String): Call<ResponseBody>
}