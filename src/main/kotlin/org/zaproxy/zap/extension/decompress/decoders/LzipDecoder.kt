package org.zaproxy.zap.extension.decompress.decoders

import java.util.zip.Inflater

class LzipDecoder : Decoder {
    override fun displayName() = "LZip"
    override fun decode(data: ByteArray): ByteArray {
        // Decompress the bytes
        val inflater = Inflater(true) //Disable wrapping header
        inflater.setInput(data, 0, data.size)
        val byteBuffer = ByteArray(data.size*10)
        val resultLength = inflater.inflate(byteBuffer)
        inflater.end()

        return byteBuffer.sliceArray(IntRange(0,resultLength-1))
    }

}