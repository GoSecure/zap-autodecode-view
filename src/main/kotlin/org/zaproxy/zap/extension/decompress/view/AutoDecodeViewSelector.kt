package org.zaproxy.zap.extension.decompress.view

import org.zaproxy.zap.extension.httppanel.Message
import org.zaproxy.zap.extension.httppanel.view.HttpPanelDefaultViewSelector

class AutoDecodeViewSelector : HttpPanelDefaultViewSelector {
    override fun getName(): String {
        return NAME
    }

    override fun matchToDefaultView(aMessage: Message?): Boolean {
        //return ResponseDecompressView.hasCspHeader(aMessage)
        return true
    }

    override fun getViewName(): String {
        return AutoDecodeView.NAME
    }

    override fun getOrder(): Int {
        return Integer.MIN_VALUE
    }

    companion object {
        val NAME = AutoDecodeViewSelector::class.java.name
    }
}