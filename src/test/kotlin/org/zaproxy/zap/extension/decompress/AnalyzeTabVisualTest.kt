package burp.org.zaproxy.zap.extension.decompress

import org.brotli.dec.BrotliInputStream
import org.hsqldb.types.Charset
import org.zaproxy.zap.extension.decompress.ui.AutoDecodeSwingUi
import java.awt.BorderLayout
import java.awt.Component
import java.io.ByteArrayInputStream
import javax.swing.JFrame
import javax.swing.JPanel

class AnalyzeTabVisualTest {

    fun iniComponent(): Component {

        val ui = AutoDecodeSwingUi()

        //val bytes = javaClass.getResourceAsStream("/samples/alice29.txt.compressed").readBytes()
        //ui.updateContent(bytes)
        //ui.updateContent("Rm9sbG93IHRoZSB3aGl0ZSByYWJiaXQ=".toByteArray(Charsets.UTF_8)) //byteArrayOf(0x0A,0x0B,0x0C)


        ui.updateContent("D%C3%A9j%C3%A0%20vu".toByteArray(Charsets.UTF_8))

        return ui.getUiComponent()
    }

}


fun main() {
    val frame = JFrame("Testing frame")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    val panel = JPanel()
    panel.layout = BorderLayout()
    panel.add(AnalyzeTabVisualTest().iniComponent(),BorderLayout.CENTER)

    frame.add(panel)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setSize(1000, 600);
    frame.isVisible = true
}