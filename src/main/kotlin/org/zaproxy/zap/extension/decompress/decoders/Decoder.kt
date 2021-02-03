package org.zaproxy.zap.extension.decompress.decoders

interface Decoder {
    fun displayName():String

    fun decode(data:ByteArray):ByteArray
}