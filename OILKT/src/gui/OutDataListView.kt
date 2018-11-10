package gui

import backend.AreaOutType
import javafx.collections.FXCollections
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview


class OutDataListView : View() {
    val areas = FXCollections.observableArrayList<AreaOutType>()

    override val root = tableview(areas) {
        readonlyColumn("X", AreaOutType::x)
        readonlyColumn("Y", AreaOutType::y)
        readonlyColumn("R", AreaOutType::r)
        readonlyColumn("S", AreaOutType::s)
//        readonlyColumn("Calc S", AreaOutType::calcS)

        prefWidth = 460.0
        prefHeight = 600.0
        columnResizePolicy = SmartResize.POLICY
    }

}