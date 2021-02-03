package org.zaproxy.zap.extension.decompress.ui

//import org.zaproxy.zap.extension.decompress.decoders.UrlUtf8Decoder
import org.exbin.deltahex.swing.CodeArea
import org.exbin.utils.binary_data.ByteArrayEditableData
import org.zaproxy.zap.extension.decompress.decoders.*
import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridLayout
import javax.swing.*


class AutoDecodeSwingUi {

    var content:ByteArray? = null

    //GUI Component
    val mainTabbedPane = JTabbedPane()
    val mainPanel = JPanel(BorderLayout())

    /* Contains both the hex editor and the text editor for the DECODE value */
    val editorDecodePanel = JPanel()
    /* Contains both the hex editor and the text editor for the ORIGINAL value */
    val editorOriginalPanel = JPanel()
    /* Description displayed at the top */
    val descriptionLabel = JLabel("",SwingConstants.CENTER)


    var isPanelInit = false
    val componentOriginalHexEditor = CodeArea()
    val componentOriginalTextArea = JTextArea(16, 58)
    val componentDecodeHexEditor = CodeArea()
    val componentDecodeTextArea = JTextArea(16, 58)

    //Original value
    var hexDataOriginal = ByteArrayEditableData()
    //Decode value
    var hexDataDecode = ByteArrayEditableData()

    companion object {
        val VERIFY_FIRST_N_CHARS = 20
    }

    fun isLegitPayload(output: ByteArray):Boolean {

        val verificationLength = Math.min(VERIFY_FIRST_N_CHARS, output.size)
        for(i in 0..verificationLength) {
            if(!(output[i].toInt() in 0x20..0x128)) {
                return false
            }
        }
        return true
    }

    //FIXME: Improve the logic here
    fun decodeAll(input: ByteArray):DecodingResult {
        val decoders = arrayOf(BrotliDecoder(), GzipDecoder(), LzipDecoder(), Base64Decoder(), UrlUtf8Decoder())

        for (d in decoders) {
            try {
                //println("Attempt to decode using "+d.javaClass.name)
                val output = d.decode(input)
                return DecodingResult(output, arrayOf(d.displayName()))
            }
            catch (e: Exception) {
            }
        }

        return DecodingResult(input, arrayOf("Unknown Format"))
    }

    fun getUiComponent(): Component {
        return getUiJComponent()
    }

    fun updateContent(currentContent: ByteArray?) {
        if(currentContent != null) {
            val decodedContent = decodeAll(currentContent)


            //println("Updating view: ${Hex.encodeHexString(currentContent)} => ${String(decodedContent.result)}")

            refreshTopDecodingDescription(mainPanel, decodedContent.algorithmNames)

            componentDecodeHexEditor.charsPerLine
            updateCodeEditors(decodedContent.result, componentDecodeHexEditor, componentDecodeTextArea)
            componentOriginalHexEditor.charsPerLine
            updateCodeEditors(currentContent, componentOriginalHexEditor, componentOriginalTextArea)

        }
    }


    fun updateCodeEditors(content:ByteArray, hexEditor:CodeArea, textArea: JTextArea) {

        hexDataDecode = ByteArrayEditableData(content)
        hexEditor.data = hexDataDecode
        hexEditor.updateUI()

        textArea.text = String(content, Charsets.UTF_8)
    }

    fun getUiJComponent(): JComponent {

        if(!isPanelInit) {
            isPanelInit = true //Not thread safe but should be enough


            updateContent(content)



            mainPanel.add(descriptionLabel, BorderLayout.NORTH)

            /*refreshTopDecodingDescription(borderPanel, arrayOf(""))*/

            addEditorsToPanel(editorDecodePanel, componentDecodeHexEditor, componentDecodeTextArea)
            addEditorsToPanel(editorOriginalPanel, componentOriginalHexEditor, componentOriginalTextArea)



            mainTabbedPane.addTab("Decoded", editorDecodePanel);
            mainTabbedPane.addTab("Original", editorOriginalPanel);




            mainPanel.add(mainTabbedPane, BorderLayout.CENTER)

        }

        return mainPanel
    }

    fun refreshTopDecodingDescription(borderPanel: JPanel, texts: Array<String>) {

        val buffer = StringBuilder()
        buffer.append("Painload ");
        for(text in texts) {
            if(text != "") {
                buffer.append("\u2794 $text")
            }
        }

        //println("Refreshing description to $buffer")
        descriptionLabel.text = buffer.toString()
    }

    fun addEditorsToPanel(editorPanel: JPanel, hexEditor:CodeArea, textArea:JTextArea) {
        editorPanel.layout = GridLayout(2, 1)


        //1. Hex editor
        hexEditor.lineLength = 32
        editorPanel.add(hexEditor)

        //2. Text area
        //Decorate the textarea with a scrollbar
        textArea.setEditable(false) // set textArea non-editable
        val scrollableTextArea = JScrollPane(textArea)
        scrollableTextArea.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED

        editorPanel.add(scrollableTextArea)
    }
}

data class DecodingResult(val result: ByteArray, val algorithmNames: Array<String>) {
}