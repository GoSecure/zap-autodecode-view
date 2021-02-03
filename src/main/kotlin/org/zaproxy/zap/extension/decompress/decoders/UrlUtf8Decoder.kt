package org.zaproxy.zap.extension.decompress.decoders

import java.lang.RuntimeException
import java.net.URLDecoder

class UrlUtf8Decoder: Decoder {

    override fun displayName() = "URL"

    override fun decode(data: ByteArray): ByteArray {
        var foundPercentSign = false
        val percentSign = ('%'.toByte())
        for(i in 0..(data.size)) {
            if(data[i] == percentSign) {
                foundPercentSign = true
                break
            }
        }
        if(!foundPercentSign) throw RuntimeException("Input data is not URL encoded")


        return URLDecoder.decode(String(data), "UTF-8").toByteArray()
        //return URLDecoder.decode(String(data,utf8), utf8).toByteArray()
    }

}