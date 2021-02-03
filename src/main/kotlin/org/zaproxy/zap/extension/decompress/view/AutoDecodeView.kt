package org.zaproxy.zap.extension.decompress.view

import org.apache.commons.configuration.FileConfiguration
import org.zaproxy.zap.extension.decompress.ui.AutoDecodeSwingUi
import org.zaproxy.zap.extension.httppanel.Message
import org.zaproxy.zap.extension.websocket.WebSocketMessageDTO
import javax.swing.JComponent
import org.zaproxy.zap.extension.httppanel.view.*

class AutoDecodeView(val model: AbstractByteHttpPanelViewModel) : HttpPanelView, HttpPanelViewModelListener {

    val ui:AutoDecodeSwingUi = AutoDecodeSwingUi()

    override fun getName(): String {
        return NAME
    }

    override fun getCaptionName(): String {
        return "Auto-Decode"
    }

    override fun getTargetViewName(): String {
        return ""
    }

    override fun getPosition(): Int {
        return 2
    }

    override fun getPane(): JComponent {
        return ui.getUiJComponent()
    }

    override fun setSelected(b: Boolean) {}
    override fun save() {}
    override fun getModel(): HttpPanelViewModel {
        return model
    }

    override fun isEnabled(message: Message?): Boolean {
        return message != null
    }

    override fun hasChanged(): Boolean {
        return false
    }

    override fun isEditable(): Boolean {
        return false
    }

    override fun setEditable(b: Boolean) {}
    override fun setParentConfigurationKey(s: String) {}
    override fun loadConfiguration(fileConfiguration: FileConfiguration) {}
    override fun saveConfiguration(fileConfiguration: FileConfiguration) {}

    override fun dataChanged(event: HttpPanelViewModelEvent) {
        val msg = model.message
        if (msg != null && msg is WebSocketMessageDTO) {
            if (msg.payload != null) {
                val payloadBytes:ByteArray = if (msg.payload is String) {
                    (msg.payload as String).toByteArray(Charsets.UTF_8)
                } else if (msg.payload is ByteArray) {
                    msg.payload as ByteArray
                } else {
                    byteArrayOf()
                }
                ui.updateContent(payloadBytes)
            }
        }
    }

    companion object {
        val NAME = AutoDecodeView::class.java.name
    }

    init {
        model.addHttpPanelViewModelListener(this)
    }
}

