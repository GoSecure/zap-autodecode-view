package org.zaproxy.zap.extension.decompress.decoders

import java.util.*

class Base64Decoder: Decoder {

    override fun displayName() = "Base64"

    override fun decode(data: ByteArray): ByteArray {
        return Base64.getDecoder().decode(data)
    }
}