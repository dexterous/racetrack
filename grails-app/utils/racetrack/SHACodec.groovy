package racetrack

import java.security.MessageDigest

class SHACodec {
  static encode(target) {
    MessageDigest.
      getInstance('SHA').
      digest(target.getBytes('UTF-8')).
      encodeBase64()
  }
}
