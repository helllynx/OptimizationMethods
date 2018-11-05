package gui

import backend.AreaOutType
import backend.OilMap
import tornadofx.View
import tornadofx.observable
import tornadofx.readonlyColumn
import tornadofx.tableview


class OutDataListView : View() {
    override val root = tableview(OilMap.outputData.observable()) {
        readonlyColumn("X", AreaOutType::x)
        readonlyColumn("Y", AreaOutType::y)
        readonlyColumn("R", AreaOutType::r)
        readonlyColumn("S", AreaOutType::s)
    }
}