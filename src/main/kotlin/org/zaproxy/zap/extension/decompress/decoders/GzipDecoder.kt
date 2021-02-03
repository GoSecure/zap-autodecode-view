package org.zaproxy.zap.extension.decompress.decoders

import java.util.zip.Inflater

class GzipDecoder : Decoder {

    override fun displayName() = "GZip"

    override fun decode(data: ByteArray): ByteArray {
        // Decompress the bytes
        val inflater = Inflater()
        inflater.setInput(data, 0, data.size)
        val byteBuffer = ByteArray(data.size*10)
        val resultLength = inflater.inflate(byteBuffer)
        inflater.end()

        return byteBuffer.sliceArray(IntRange(0,resultLength-1))
    }

}