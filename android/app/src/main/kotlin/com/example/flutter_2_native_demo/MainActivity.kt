package com.example.flutter_2_native_demo

import android.util.Log
import com.example.flutter_2_native_demo.repositories.PokemonImageRepositoryImpl
import com.example.flutter_2_native_demo.services.PokemonImageServiceImpl
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : FlutterActivity() {
    private val tag = "MainActivity"

    private val channelName = "pokemon_image_event_channel"

    private val imageService = PokemonImageServiceImpl(PokemonImageRepositoryImpl())

    private var disposable: Disposable? = null

    private var eventChannel: EventChannel? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        eventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, channelName)

        eventChannel!!.setStreamHandler(
            object : EventChannel.StreamHandler {
                override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
                    Log.i(tag, "Iniciando streaming de imagens")

                    disposable = imageService.getPokemonImageBytes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeWith { byteArrayOf() }
                        .subscribe { byteArray ->
                            run {
                                if (byteArray.isNotEmpty()) {
                                    eventSink?.success(byteArray)
                                    Log.i(tag, "Imagem enviada")
                                } else {
                                    Log.e(tag, "Falha no carregamento da imagem")
                                }
                            }
                        }
                }

                override fun onCancel(arguments: Any?) {
                    Log.i(tag, "Cancelando streaming de imagens")

                    disposable?.dispose()
                }
            }
        )
    }

    override fun onDestroy() {
        disposable?.dispose()

        super.onDestroy()
    }
}
