import 'dart:typed_data';

abstract class PokemonImageService {
  Stream<Uint8List> getImages();
}
