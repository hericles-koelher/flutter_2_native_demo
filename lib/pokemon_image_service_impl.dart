import 'dart:typed_data';

import 'package:flutter/services.dart';
import 'package:flutter_2_native_demo/pokemon_image_service.dart';

class PokemonImageServiceImpl implements PokemonImageService {
  static const String _channelName = "pokemon_image_event_channel";
  final EventChannel _eventChannel;

  PokemonImageServiceImpl() : _eventChannel = const EventChannel(_channelName);

  @override
  Stream<Uint8List> getImages() {
    return _eventChannel.receiveBroadcastStream().cast<Uint8List>();
  }
}
