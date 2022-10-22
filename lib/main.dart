import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter_2_native_demo/pokemon_image_service.dart';
import 'package:flutter_2_native_demo/pokemon_image_service_impl.dart';

void main() {
  runApp(const Flutter2NativeDemo());
}

class Flutter2NativeDemo extends StatelessWidget {
  static const String appName = "Flutter 2 Native Demo";

  const Flutter2NativeDemo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: appName,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const HomePage(
        appName: appName,
      ),
    );
  }
}

class HomePage extends StatefulWidget {
  final String appName;

  const HomePage({Key? key, required this.appName}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final PokemonImageService pokemonImageService = PokemonImageServiceImpl();
  var showImages = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.appName),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            Expanded(
              flex: 2,
              child: showImages
                  ? StreamBuilder<Uint8List>(
                      stream: pokemonImageService.getImages(),
                      builder: (context, snapshot) {
                        return snapshot.hasData && showImages
                            ? Padding(
                                padding: const EdgeInsets.symmetric(
                                  horizontal: 25.0,
                                ),
                                child: Image.memory(snapshot.data!),
                              )
                            : Container(
                                color: Colors.transparent,
                              );
                      },
                    )
                  : Container(
                      color: Colors.transparent,
                    ),
            ),
            Flexible(
              child: ElevatedButton(
                onPressed: () {
                  setState(() {
                    showImages = !showImages;
                  });
                },
                child: Text(showImages ? "Cancelar" : "Iniciar"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
