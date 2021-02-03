package org.zaproxy.zap.extension.decompress.decoders

import org.brotli.dec.BrotliInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

class BrotliDecoder : Decoder {

    override fun displayName() = "Brotli"

    override fun decode(data: ByteArray): ByteArray {
        return BrotliInputStream(ByteArrayInputStream(data)).readBytes()
    }

}