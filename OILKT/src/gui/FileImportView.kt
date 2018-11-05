package gui

import backend.OilMap
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileImportView : View() {

    private val kryo = Kryo()

    private val fileTypeFilterTXT = arrayOf(FileChooser.ExtensionFilter("Data files (*.txt, *.bin)", "*.txt", "*.bin"))
    private val fileTypeFilterBIN = arrayOf(FileChooser.ExtensionFilter("Data files (*.txt, *.bin)", "*.txt", "*.bin"))
    private lateinit var file: String
    private lateinit var files: List<File>

    override val root = vbox {
        setMinSize(460.0, 600.0)
        button("Read txt") {
            action {
                files = chooseFile("Open ", fileTypeFilterTXT)
                file = if (files.isEmpty()) "" else files[0].absolutePath
                if (!file.isEmpty()) {
                    OilMap.importOilMap = backend.parse(file)
                    val outputFS = Output(FileOutputStream(file.substring(file.lastIndexOf("/") + 1) + ".bin"))
                    kryo.writeObject(outputFS, OilMap.importOilMap)
                    outputFS.close()
                    println("Read txt map: $file")
                    replaceWith<DataInputView>()
                } else {
                    alert(
                        type = Alert.AlertType.ERROR,
                        header = "Please select file! (*.txt)",
                        actionFn = { btnType ->
                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                            }
                        }
                    )
                }
            }
        }

        button("Import bin") {
            action {
                files = chooseFile("Open ", fileTypeFilterBIN)
                file = if (files.isEmpty()) "" else files[0].absolutePath
                if (!file.isEmpty()) {
                    kryo.register(OilMap::class.java)
                    kryo.register(OilMap.MapType::class.java)
                    kryo.register(ArrayList::class.java)
                    kryo.register(FloatArray::class.java)
                    val inputFS = Input(FileInputStream(file))
                    OilMap.importOilMap = kryo.readObject(inputFS, backend.OilMap::class.java)
                    inputFS.close()
                    println("Import serialized map: $file")
                    replaceWith<DataInputView>()
                } else {
                    alert(
                        type = Alert.AlertType.ERROR,
                        header = "Please select file! (*.bin)",
                        actionFn = { btnType ->
                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                            }
                        }
                    )
                }
            }
        }
    }
}