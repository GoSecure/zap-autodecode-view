package org.zaproxy.zap.extension.decompress

import org.parosproxy.paros.extension.Extension
import org.parosproxy.paros.extension.ExtensionAdaptor
import org.parosproxy.paros.extension.ExtensionHook
import org.zaproxy.zap.extension.decompress.view.AutoDecodeView
import org.zaproxy.zap.extension.httppanel.view.HttpPanelView
import org.zaproxy.zap.extension.websocket.ui.httppanel.component.WebSocketComponent
import org.zaproxy.zap.extension.websocket.ui.httppanel.models.ByteWebSocketPanelViewModel
import org.zaproxy.zap.view.HttpPanelManager

class AutoDecodeExtension : ExtensionAdaptor(), Extension {

    override fun getAuthor(): String {
        return "Philippe Arteau"
    }

    override fun hook(extensionHook: ExtensionHook) {
        super.hook(extensionHook)
        if (view != null) {
            val panelManager = HttpPanelManager.getInstance()

            val viewFactory = AutoDecodeViewFactory()
            panelManager.addRequestViewFactory(WebSocketComponent.NAME, viewFactory)
            panelManager.addResponseViewFactory(WebSocketComponent.NAME, viewFactory)

        }
    }

    override fun unload() {
        if (view != null) {
            val panelManager = HttpPanelManager.getInstance()

            panelManager.removeRequestViewFactory(WebSocketComponent.NAME, AutoDecodeViewFactory.NAME)
            panelManager.removeResponseViewFactory(WebSocketComponent.NAME, AutoDecodeViewFactory.NAME)
        }
    }

}

class AutoDecodeViewFactory : HttpPanelManager.HttpPanelViewFactory {


    override fun getName(): String {
        return NAME
    }

    override fun getNewView(): HttpPanelView {
        return AutoDecodeView(ByteWebSocketPanelViewModel())
    }

    override fun getOptions(): Any? {
        return null
    }

    companion object {
        val NAME = AutoDecodeViewFactory::class.java.name
    }
}